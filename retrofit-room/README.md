# Retrofit + Room baza (zadatak 5)

**Cilj:** Model posta u bazi + Retrofit GET na  
`https://app.beeceptor.com/mock-server/dummy-json`

## Gde u projektu

| Fajl | Putanja |
|------|---------|
| Post (entitet) | `app/src/main/java/.../model/Post.java` |
| DAO | `app/src/main/java/.../db/PostDao.java` |
| Baza | `app/src/main/java/.../db/AppDatabase.java` |
| API interfejs | `app/src/main/java/.../api/JsonPlaceholderApi.java` |
| Retrofit | `app/src/main/java/.../api/RetrofitClient.java` |

## Kako napraviti

1. **Sync Gradle** sa Retrofit + Room zavisnostima (`osnovni-projekat/gradle-zavisnosti.txt`)
2. Kreiraj pakete: `model`, `db`, `api`
3. Kopiraj Java fajlove iz ovog foldera
4. U `MainActivity` inicijalizuj bazu:  
   `AppDatabase db = Room.databaseBuilder(...).build();`

## API odgovor

Endpoint vraća JSON **niz** objekata:

```json
[
  { "userId": 1, "id": 1, "title": "...", "body": "..." },
  ...
]
```

Model `Post` pokriva polja `id`, `title`, `body`, `userId`. Dodatna polja (npr. `link`) Gson ignoriše.

**Napomena:** URL sa kolokvijuma (`https://app.beeceptor.com/mock-server/dummy-json`) može da preusmeri. Ako ne radi, proveri na času tačan URL ili koristi alternativu:

- Base URL: `https://dummy-json.mock.beeceptor.com/`
- Putanja: `posts` → promeni u `JsonPlaceholderApi` na `@GET("posts")` i u `RetrofitClient` base URL.

## Fajlovi u folderu

- `Post.java` – Room entitet (`@Entity`)
- `PostDao.java` – insert, delete, getAll, getFirst
- `AppDatabase.java` – Room baza
- `JsonPlaceholderApi.java` – Retrofit interfejs
- `RetrofitClient.java` – singleton Retrofit

## Checklist

- [ ] `@Entity` na Post
- [ ] `@PrimaryKey` na id
- [ ] DAO sa `@Insert`, `@Query`, `@Delete`
- [ ] Retrofit sa `.baseUrl("https://app.beeceptor.com/")`
- [ ] GET putanja: `mock-server/dummy-json`
