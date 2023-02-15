public class Simon
{
    public static void main(String[] args)
    {
        /* Окно запуска новый интерфейс

         */
        Game game = new Game();

        Ui ui = new Ui(game);
        ui.setLocation(500,120);
        game.registerObserver(ui);
        game.play();
        /*
        Создание окна приложения и запуск Game
         */

    }

}