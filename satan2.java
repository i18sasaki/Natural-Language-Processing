
import java.util.ArrayDeque;
import java.util.Queue;
public class satan2{
    public static String Vn[]={"S","A","B"};
    public static String Vt[]={"a","b","c","d","e"};
    public static String P1[][]={{"S","Aa"},{"A","Ab","B"},{"B","Bc","Bd","e"}};
    public static Queue<String> dervlist = new ArrayDeque<String>();
    public static String derv="S";



    public static void main(String args[]){
        dervlist.add(derv);
        while(dervlist.size()<1000){
            String s=dervlist.poll();
            int min=-1,minj=-1;
            for(int k=0;k<s.length();k++){
                for(int j=0;j<P1.length;j++){
                    if(P1[j][0].charAt(0)==s.charAt(k)){
                        if(min<0){
                            min=k;
                            minj=j;
                        }else{
                            if(min>k){
                                min=k;
                                minj=j;
                            }
                        }
                    }
                }
            }
            for(int j=1;j<P1[minj].length;j++){
                String ns="";
                int fl=0;
                for(int k=0;k<s.length();k++){
                    if(P1[minj][0].charAt(0)==s.charAt(k)&&fl==0){
                        ns+=P1[minj][j];
                        fl=1;
                        //System.out.printf("%d-%s-%s-%s\n",nm,P1[j][0],s,ns);
                    }
                    else{
                        ns+=s.charAt(k);
                    }
                    
                }
                int f=0;
                for(int k=0;k<s.length();k++){
                    for(int l=0;l<Vn.length;l++){
                        if(ns.charAt(k)==Vn[l].charAt(0)){
                            f=1;
                            dervlist.add(ns);
                            break;
                        }
                    }
                    if(f==1){
                        break;
                    }
                }
                if(f==0){
                    System.out.printf("%s\n",ns);
                }
            }
            //System.out.println(dervlist);
        }
    }
}