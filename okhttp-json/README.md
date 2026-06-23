# OkHttp – ručno čitanje JSON-a (bez Retrofit-a)

**Slično:** Retrofit GET (zadatak 5), ali sa nižeg nivoa HTTP klijentom.

**Mogući zadatak:** U pozadinskoj niti preuzmi JSON sa URL-a, parsiraj Gson-om, upiši u bazu.

## Gradle

```gradle
implementation 'com.squareup.okhttp3:okhttp:4.12.0'
implementation 'com.google.code.gson:gson:2.10.1'
```

## Fajlovi

- `OkHttpJsonSegment.java`

## Obrazac

1. `OkHttpClient` + `Request`
2. `client.newCall(request).execute()` u pozadini
3. `Gson` → `List<Post>`
4. UI na main thread

## Checklist

- [ ] `INTERNET` dozvola
- [ ] Ne pozivaj `execute()` na main niti
- [ ] `TypeToken` za listu generičkih tipova
