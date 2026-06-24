package com.example.kolokvijum2;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * CameraX – kamera unutar aplikacije (live preview, kao Instagram story).
 * Folder: 86-camerax/
 */
public class CameraXFragment extends Fragment {

    public interface OnPhotoReadyListener {
        void onPhotoReady(Uri uri);
    }

    private PreviewView previewView;
    private ImageView imageView;
    private Button captureButton;
    private ImageButton backButton;

    private ImageCapture imageCapture;
    private File lastPhotoFile;
    private Uri lastPhotoUri;

    private ActivityResultLauncher<String> permissionLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        startCamera();
                    } else {
                        toast("Potrebna je dozvola za kameru");
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camerax, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        previewView = view.findViewById(R.id.previewView);
        imageView = view.findViewById(R.id.imageView);
        captureButton = view.findViewById(R.id.captureButton);
        backButton = view.findViewById(R.id.backButton);

        captureButton.setOnClickListener(v -> capturePhoto());
        backButton.setOnClickListener(v -> finishWithResult());

        checkPermission();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> future =
                ProcessCameraProvider.getInstance(requireContext());

        future.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = future.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();

                CameraSelector cameraSelector;
                if (cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)) {
                    cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
                } else if (cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)) {
                    cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA;
                } else {
                    toast("Kamera nije dostupna");
                    return;
                }

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(
                        getViewLifecycleOwner(),
                        cameraSelector,
                        preview,
                        imageCapture
                );
            } catch (Exception e) {
                e.printStackTrace();
                toast("Greška pri pokretanju kamere");
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    private void capturePhoto() {
        if (imageCapture == null) {
            toast("Kamera nije spremna");
            return;
        }

        lastPhotoFile = new File(
                requireContext().getExternalFilesDir(null),
                "IMG_" + System.currentTimeMillis() + ".jpg"
        );

        ImageCapture.OutputFileOptions options =
                new ImageCapture.OutputFileOptions.Builder(lastPhotoFile).build();

        imageCapture.takePicture(
                options,
                ContextCompat.getMainExecutor(requireContext()),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        showSaveChoiceDialog();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        exception.printStackTrace();
                        toast("Snimanje nije uspelo");
                    }
                }
        );
    }

    private void showSaveChoiceDialog() {
        String[] options = {"Folder aplikacije", "Galerija", "Vrati u aplikaciju"};

        new AlertDialog.Builder(requireContext())
                .setTitle("Sačuvaj sliku")
                .setItems(options, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            showThumbnail(Uri.fromFile(lastPhotoFile));
                            toast("Sačuvano u folder aplikacije");
                            break;
                        case 1:
                            Uri galleryUri = saveToGallery(lastPhotoFile);
                            if (galleryUri != null) {
                                showThumbnail(galleryUri);
                                toast("Sačuvano u galeriju");
                            } else {
                                toast("Čuvanje u galeriju nije uspelo");
                            }
                            break;
                        case 2:
                            lastPhotoUri = Uri.fromFile(lastPhotoFile);
                            finishWithResult();
                            break;
                    }
                })
                .show();
    }

    private void showThumbnail(Uri uri) {
        lastPhotoUri = uri;
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageURI(uri);
    }

    @Nullable
    private Uri saveToGallery(File file) {
        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, file.getName());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Kolokvijum2");
            }

            Uri uri = requireContext().getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri == null) {
                return null;
            }

            try (FileInputStream in = new FileInputStream(file);
                 OutputStream out = requireContext().getContentResolver().openOutputStream(uri)) {
                if (out == null) {
                    return null;
                }
                byte[] buffer = new byte[8192];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            }
            return uri;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void finishWithResult() {
        if (!(requireActivity() instanceof OnPhotoReadyListener)) {
            requireActivity().finish();
            return;
        }
        if (lastPhotoUri != null) {
            ((OnPhotoReadyListener) requireActivity()).onPhotoReady(lastPhotoUri);
        } else {
            requireActivity().finish();
        }
    }

    private void toast(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
