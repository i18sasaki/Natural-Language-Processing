import java.util.ArrayDeque;
import java.util.Deque;

import javax.swing.plaf.synth.SynthEditorPaneUI;

public class Rporand{
    
    public static char operate[][]={{'+','-'},{'*','/'},{'('},{')'}};
    public static Deque<Character> opque = new ArrayDeque<>();
    public static Deque<Integer> pryque = new ArrayDeque<>();
    public static Deque<Character> valque = new ArrayDeque<>();
    public static int[] is_op(char c){
        int a[]={-1,-1};
        for(int i=0;i<operate.length;i++){
            for(int j=0;j<operate[i].length;j++){
                if(c==operate[i][j]){
                    a[0]=i;
                    a[1]=j;
                }
            }
        }
        return a;
    }
    public static void main(String args[]){
        String s="A+B*(C*D)+E+F*G+H";
        String Rs="";
        char c;
        int pry=0;
        if(args.length==1){
            s=args[0];
        }
        for(int i=0;i<s.length();i++){
            c=s.charAt(i);
            if(Character.isAlphabetic(c)){
                valque.push(c);
                Rs+=String.valueOf(c);
                //System.out.printf("%c\n",c);
            }
            else{
                int op_ind[]=is_op(c);
                if(op_ind[0]==-1){
                    System.out.printf("error");
                    break;
                }
                if(op_ind[0]==2){
                    pry+=2;
                }
                else if(op_ind[0]==3){
                    pry-=2;
                }
                else{
                    if(opque.size()==0){
                        opque.push(c);
                        pryque.push(pry+op_ind[0]);    
                        //System.out.printf("%c:%d-%d\n",c,0,pry+op_ind[0]);
                    }else{
                        int prepry=pryque.peek();
                        //System.out.println(pryque);
                        //System.out.printf("%c:%d-%d\n",c,prepry,pry+op_ind[0]);
                        if(prepry>=pry+op_ind[0]){
                            Rs+=String.valueOf(opque.pop());
                            pryque.pop();
                            //System.out.printf("!!%d\n",prepry);
                            while(pryque.size()>0){
                                Rs+=String.valueOf(opque.pop());
                                if(pryque.pop()<=pry+op_ind[0]){
                                    break;
                                }
                            }
                        }
                        opque.push(c);
                        pryque.push(pry+op_ind[0]);
                    }
                } 
            }
        }
        while(opque.size()>0){
            Rs+=String.valueOf(opque.pop());
        }
        if(pry!=0){
            System.out.printf("error%d",pry);
        }
        System.out.printf("逆ポーランド記法:%s",Rs);
    }
}