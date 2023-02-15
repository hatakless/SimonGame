public interface Subject
{
    public void registerObserver(Observe o);
    public void removeObserver(Observe o);
    public void notifyObservers();
    /*
    Регистрация, удаление и уведомление.
     */
}