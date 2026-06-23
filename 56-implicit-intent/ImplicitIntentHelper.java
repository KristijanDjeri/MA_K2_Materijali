package com.example.kolokvijum2.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kolokvijum2.model.Post;

public class ImplicitIntentHelper {

    public static void otvoriUrl(AppCompatActivity activity, String url) {
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public static void podeliTekst(AppCompatActivity activity, String tekst) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, tekst);
        activity.startActivity(Intent.createChooser(intent, "Podeli"));
    }

    public static void posaljiSms(AppCompatActivity activity, String broj, String poruka) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + broj));
        intent.putExtra("sms_body", poruka);
        activity.startActivity(intent);
    }

    public static void posaljiEmail(AppCompatActivity activity, String adresa,
                                    String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + adresa));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        activity.startActivity(intent);
    }

    public static void otvoriMapu(AppCompatActivity activity, double lat, double lon) {
        Uri geo = Uri.parse("geo:" + lat + "," + lon + "?q=" + lat + "," + lon);
        activity.startActivity(new Intent(Intent.ACTION_VIEW, geo));
    }

    public static void podeliTitlePrvogPosta(AppCompatActivity activity, Post post) {
        if (post != null) {
            podeliTekst(activity, post.getTitle());
        }
    }
}
