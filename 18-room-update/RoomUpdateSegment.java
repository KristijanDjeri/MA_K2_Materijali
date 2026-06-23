// Deo za MainActivity (folder 18-room-update/)

private void izmeniTitlePrvogPosta(String noviTitle) {
    Post prvi = postDao.getFirst();
    if (prvi != null) {
        prvi.setTitle(noviTitle);
        postDao.update(prvi);
        Toast.makeText(this, "Ažurirano: " + noviTitle, Toast.LENGTH_SHORT).show();
    } else {
        Toast.makeText(this, "Nema postova", Toast.LENGTH_SHORT).show();
    }
}
