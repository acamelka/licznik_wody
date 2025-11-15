public class Suma {
    private int suma;
    private int procent;

    public int get_suma_(){return suma;}
    public void set_suma_(int x,int oczekiwana){suma=x; set_procent_(oczekiwana);}

    public int get_procent_(){return procent;}
    private void set_procent_(int oczekiwana){ procent= (int) (suma * 100) / oczekiwana;}

    public void dodaj_(int ile,int oczekiwana){
          suma+=ile;
          set_procent_(oczekiwana);

    }
}
