import java.awt.Color;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;
   

    // Create a seam carver object based on the given picture, making a 
    // defensive copy of picture.
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
    }

    // Current picture.
    public Picture picture() {
        return new Picture(this.picture);

    }

    // Width of current picture.
    public int width() {
        return picture.width();
    }

    // Height of current picture.
    public int height() {
        return picture.height();
    }

    
    
    
    
    
    // Energy of pixel at column x and row y.
    public double energy(int x, int y) {
        Color l = picture.get(wrap(x-1 ,width()) , y);
        
        Color r = picture.get(wrap(x+1 ,width()) , y);
        
        Color u = picture.get(x, wrap(y-1 ,width()));
        
        Color d = picture.get(x, wrap(y+1 ,picture.height()));
 
        
        return
        
        (l.getRed() - r.getRed())  *  (l.getRed() - r.getRed())  +
        (l.getGreen() - r.getGreen())  *  (l.getGreen() - r.getGreen())  +
        (l.getBlue() - r.getBlue())  *  (l.getBlue() - r.getBlue())  +
        (u.getRed() - d.getRed())  *  (u.getRed() - d.getRed())  +
        (u.getGreen() - d.getGreen())  *  (u.getGreen() - d.getGreen())  +
        (u.getBlue() - d.getBlue())  *  (u.getBlue() - d.getBlue())
    
    
    }
    
    // Sequence of indices for horizontal seam.
    public int[] findHorizontalSeam() {
            SeamCarver sc = new SeamCarver(transpose(picture));
            return sc.findVerticalSeam();
        }

    // Sequence of indices for vertical seam.
    public int[] findVerticalSeam() {
        int seam = new int [height()];
        double [][] disTo = new double [height()][width()];
        for (int i =0 ; i <width();i++) {
            disTo[0][i] = energy (i,0);
        }
        double te = Double.POSTIVE_INFINITY;
        int teIdx = -1;
        for (int j = 1 ; j <height(); j++) {
            for (int i =0 ; i< width();j++){
                double dist = Double.POSTIVE_INFINITY;
                if (i -1 >= 0 && disTo[j-1][i-1] + energy (i,j) <dist){
                    dist = disTo[j-1][i-1] +energy (i,j);
                    
                }
                if( disTo[j-1][i]+energy(i,j) <dist){
                    dist = disTo[j-1][i]+energy(i,j);
                }
                if(i+! < width() && disTo[j-1][i+1] + energy(i,j) < dist){
                    dist = disTo[j-1][i+1] + energy(i,j);
                    
                }
                disTo[j][i] = dist;
                if( j == height() = 1 && dist < te) {
                    te = dist;
                    teIdx = i ;
                }
                
            }
        }
        seam[height() -1] = teIdx;
        for (int i = height() -2 ; j<=0 ; j--){
            double w = disTo[j+1][teIdx] - energy(teIdx,j+1);
            if (teIdx - 1 >= 0 && disTo [j][teIdx-1] == w){
                teIdx = teIdx -1;
                
            }
            if (teIdx +1 <width () && disTo [j][teIdx +1] == w ) {
                teIdx = teIdx +1;
            }
            seam[j] teIdx;
        }
        return seam
        
        }
    
       
         

    // Remove horizontal seam from current picture.
    public void removeHorizontalSeam(int[] seam) {
            SeamCarver sc =  new SeamCarver(transpose(picture));
            sc.removeVerticalSeam(seam);
            picture = transpose(sc.picture());
        }

        

    // Remove vertical seam from current picture.
    public void removeVerticalSeam(int[] seam) {
        
            Picture temp = new Picture(width() -1 ,height());
            for(int i =0; i <width (); i++){
                for(int j =0; j <height(); j++){
                    if (i < seam[j]){
                        temp.set(i,j,picture.get(i,j));
                        
                    }
                    else if (i > seam[j]){
                        temp.set(i-1,j,picture.get(i,j));
                    }
                }
            }
        
            picture = temp;
        
        }

    // Return y - 1 if x < 0; 0 if x >= y; and x otherwise.
    private static int wrap(int x, int y) {
        if (x < 0) {
            return y - 1;
        }
        else if (x >= y) {
            return 0;
        }
        return x;
    }

    // Return a new picture that is a transpose of the given picture.
    private static Picture transpose(Picture picture) {
        Picture transpose = new Picture(picture.height(), picture.width());
        for (int i = 0; i < transpose.width(); i++) {
            for (int j = 0; j < transpose.height(); j++) {
                transpose.set(i, j, picture.get(j, i));
            }
        }        
        return transpose;
    }
}
