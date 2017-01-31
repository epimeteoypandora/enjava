
import general.ReadFile;
import layers.application.LocalApplication;

public class start {
    public static void main(String[] args) {
        int[][] image=ReadFile.readImage("./imagen2.txt");
        LocalApplication applicationLayer = new LocalApplication(image);
        applicationLayer.start();    
      
    }
}
