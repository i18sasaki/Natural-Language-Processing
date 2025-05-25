import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class FirstStract{
    String input;
    List<String> First_input=new ArrayList<String>();
}

class FollowStract{
    String input;
    List<String> Follow_input=new ArrayList<String>();
}

class DirectorStract{
    String input1;
    String input2;
    List<String> Director_input=new ArrayList<String>();
}

public class FFD{
    public static String Vn[]={"S","A","A'","B","B'"};
    public static String Vt[]={"a","b","c","d","e"};
    public static String V[]=new String[Vn.length+Vt.length+1];
    public static String P1[][]={{"S","Aa"},{"A","BA'"},{"A'","bA'","ε"},{"B","eB'"},{"B'","cB'","dB'","ε"}};
    public static String derv="S";
    public static List<FirstStract> first_list = new ArrayList<FirstStract>();
    public static List<FollowStract> follow_list = new ArrayList<FollowStract>();
    public static List<DirectorStract> director_list = new ArrayList<DirectorStract>();

    public static int Is_V(String s){
        for(int i=0;i<V.length;i++){
            if(V[i].equals(s)){
                return i;
            }
        }
        return -1;
    }

    public static String Head_V(String s){
        for(int i=0;i<V.length;i++){
            int f=1;
            for(int j=0;j<V[i].length();j++){
                if(V[i].length()<=s.length()){
                    if(V[i].charAt(j)!=s.charAt(j)){
                        f=0;
                    }
                }
            }
            if(f==1){
                if(V[i].length()<s.length()){
                    for(int j=0;j<V.length;j++){
                        if(V[j].charAt(0)==s.charAt(V[i].length())){
                            return V[i];
                        }
                    }
                }
                else if(V[i].length()==s.length()){
                    return V[i];
                }
            }
        }
        return null;
    }

    public static void HeadSearch(String s,List<String> f){
        s=Head_V(s);
        int v_p=Is_V(s);
        //System.out.printf("%s %d\n",s,v_p);
        if(v_p<0){
            System.out.println("構文エラー");
            System.exit(1);
        }
        else if(v_p>=Vn.length){
            f.add(s);
        }
        else{
            for(int j=1;j<P1[v_p].length;j++){
                //System.out.printf("%s->%s\n",P1[v_p][0],P1[v_p][j]);
                HeadSearch(P1[v_p][j],f);
            }
        }
        //System.out.printf("---------\n\n");
    }

    public static void TailSearch(String s,List<String> f){
        for(int i=0;i<P1.length;i++){
            for(int j=1;j<P1[i].length;j++){
                String ms=P1[i][j];
                int ep=0;
                while(ep<2){
                    String hw="",bw="";
                    hw=Head_V(ms);
                    if(ep==1){
                        for(int l=0;l<first_list.get(Is_V(hw)).First_input.size();l++){
                            if(first_list.get(Is_V(hw)).First_input.get(l)!="ε"){
                                f.add(first_list.get(Is_V(hw)).First_input.get(l));
                            }
                        }
                    }
                    if(hw.length()<ms.length()){
                        for(int k=hw.length();k<ms.length();k++){
                            bw+=ms.charAt(k);
                        }
                        if(hw.equals(s)){
                            ep=1;
                        }
                    }
                    else{
                        if(hw.equals(s)){
                            if(s.equals(P1[i][0])){}
                            else{
                                if(ep==0){
                                    for(int l=0;l<follow_list.get(Is_V(P1[i][0])).Follow_input.size();l++){
                                        if(follow_list.get(Is_V(P1[i][0])).Follow_input.get(l)!="ε"){
                                            f.add(follow_list.get(Is_V(P1[i][0])).Follow_input.get(l));
                                        }
                                    }
                                }
                            }
                        }
                        ep=2;
                    }
                    ms=bw;
                }
            }
        }
    }

    public static void main(String args[]){
        for(int i=0;i<Vn.length;i++){
            V[i+Vn.length]=Vt[i];
            FirstStract fs=new FirstStract();
            fs.input=Vn[i];
            first_list.add(fs);
        }
        for(int i=0;i<Vt.length;i++){
            V[i]=Vn[i];
            FirstStract fs=new FirstStract();
            fs.input=Vt[i];
            first_list.add(fs);
        }
        V[Vn.length+Vt.length]="ε";
        for(int i=0;i<first_list.size();i++){
            FirstStract fs=first_list.get(i);
            HeadSearch(fs.input,fs.First_input);
            first_list.set(i,fs);
            System.out.printf("First(%s)={%s",fs.input,fs.First_input.get(0));
            for(int j=1;j<fs.First_input.size();j++){
                System.out.printf(",%s",fs.First_input.get(j));
            }
            System.out.printf("}\n");
        }
        for(int i=0;i<Vn.length;i++){
            FollowStract fs=new FollowStract();
            fs.input=Vn[i];
            follow_list.add(fs);
        }
        for(int i=0;i<follow_list.size();i++){
            FollowStract fs=follow_list.get(i);
            if(fs.input.equals(derv)){
                fs.Follow_input.add("$");
            }
            TailSearch(fs.input,fs.Follow_input);
            follow_list.set(i,fs);
            System.out.printf("Follow(%s)={%s",fs.input,fs.Follow_input.get(0));
            for(int j=1;j<fs.Follow_input.size();j++){
                System.out.printf(",%s",fs.Follow_input.get(j));
            }
            System.out.printf("}\n");
        }
        for(int i=0;i<P1.length;i++){
            for(int j=1;j<P1[i].length;j++){
                DirectorStract ds=new DirectorStract();
                ds.input1=P1[i][0];
                ds.input2=P1[i][j];
                //System.out.printf("%s%s",Is_V(Head_V(ds.input1)),ds.input2);
                FollowStract fls=follow_list.get(Is_V(Head_V(ds.input1)));
                
                if(ds.input2=="ε"){
                    for(int k=0;k<fls.Follow_input.size();k++){
                        ds.Director_input.add(fls.Follow_input.get(k));
                    }
                }else{
                    FirstStract fs=first_list.get(Is_V(Head_V(ds.input2)));
                    for(int k=0;k<fs.First_input.size();k++){
                        if(fs.First_input.get(k)!="ε"){
                            ds.Director_input.add(fs.First_input.get(k));
                        }
                        else{
                            for(int l=0;l<fls.Follow_input.size();l++){
                                ds.Director_input.add(fls.Follow_input.get(l));
                            } 
                        }
                    }
                }
                director_list.add(ds);
            }
        }
        for(int i=0;i<director_list.size();i++){
            DirectorStract ds=director_list.get(i);
            System.out.printf("Director(%s,%s)={%s",ds.input1,ds.input2,ds.Director_input.get(0));
            for(int k=1;k<ds.Director_input.size();k++){
                System.out.printf(",%s",ds.Director_input.get(k));
            }
            System.out.printf("}\n");
        }
    }
}