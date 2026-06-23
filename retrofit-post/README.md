# Retrofit POST zahtev

**Slično:** Retrofit GET + Room (zadatak 5), ali slanje podataka na server.

**Mogući zadatak:** Dugme šalje novi post na API (`POST`) ili mock endpoint prihvata JSON telo.

## Gde u projektu

| Fajl | Putanja |
|------|---------|
| API interfejs | `JsonPlaceholderApi.java` |
| Poziv | `MainActivity.java` |

## Koraci

1. U interfejs dodaj `@POST` metodu
2. Telo zahteva: `@Body Post post`
3. `RetrofitClient.getApi().createPost(post).enqueue(...)`

## Napomena

Mock server možda ne čuva POST – dovoljno je da u `onResponse` prikažeš Toast "Poslato".  
Za test možeš koristiti `https://jsonplaceholder.typicode.com/posts` (često na vežbama).

## Fajlovi

- `JsonPlaceholderApiPost.java` – POST metoda
- `RetrofitPostSegment.java`

## Checklist

- [ ] `@POST` anotacija
- [ ] `@Body` parametar
- [ ] `enqueue` callback
- [ ] Toast uspeh/greška
