import java.awt.*;
import java.awt.event.*;
public class app20_10b extends Frame implements MouseMotionListener,MouseListener,ActionListener
{
   static app20_10b frm = new app20_10b();
   static MenuBar mb = new MenuBar();//建立MenuBar物件
   static Menu menu1 = new Menu("Color");//建立Menu物件
   static Menu menu2 = new Menu("Size");
   static Menu menu3 = new Menu("Tool");
   static MenuItem mi[] = new MenuItem[12];//宣告MenuItem物件
   static Dialog dlg = new Dialog(frm,"Set color");//建立Dialog物件
   static TextField tx[] = new TextField[4];//宣告TextField物件
   static Button Ok_btn = new Button("Ok");//建立Button物件
   static Button Cancel_btn = new Button("Cancel");
   public static void main(String args[]) 
   {
      //Frame排版
      frm.setTitle("小畫板");
      frm.setSize(800,650);
      frm.setMenuBar(mb);//設定frm的功能表為mb
      frm.addMouseListener(frm);
      frm.addMouseMotionListener(frm);
      frm.setResizable(false);//大小不可改變 因為paint()有bug(?
      frm.setVisible(true);
      //關閉
      frm.addWindowListener(new WindowAdapter(){
         public void windowClosing(WindowEvent e){System.exit(0);}});
      //MenuBar排版
      mb.add(menu1);//menu加入menubar
      mb.add(menu2);
      mb.add(menu3);
      mi[0]=new MenuItem("Black");//建立MenuItem物件 mi[]
      mi[1]=new MenuItem("Red");
      mi[2]=new MenuItem("Yellow");
      mi[3]=new MenuItem("Blue");
      mi[4]=new MenuItem("Other");
      mi[5]=new MenuItem("Small");
      mi[6]=new MenuItem("Middle");
      mi[7]=new MenuItem("Large");
      mi[8]=new MenuItem("Other");
      mi[9]=new MenuItem("Pen");
      mi[10]=new MenuItem("Eraser");
      mi[11]=new MenuItem("Clean");
      for(int i=0;i<5;i++)//mi[]加入menu
         menu1.add(mi[i]);
      for(int i=5;i<9;i++)//mi[]加入menu
         menu2.add(mi[i]);
      for(int i=9;i<12;i++)//mi[]加入menu
         menu3.add(mi[i]);
      for(int i=0;i<12;i++)//將mi[]註冊
         mi[i].addActionListener(frm);
      //TextField排版
      tx[0]=new TextField("R(0~255)");//建立TextField物件 tx[]
      tx[0].setBounds(20,50,60,20);   //並設定大小位子
      tx[1]=new TextField("G(0~255)");
      tx[1].setBounds(20,80,60,20);
      tx[2]=new TextField("B(0~255)");
      tx[2].setBounds(20,110,60,20);
      tx[3]=new TextField("SIZE");
      tx[3].setBounds(20,50,60,20);
      //Button排版
      Ok_btn.setBounds(100,50,60,20);
      Cancel_btn.setBounds(100,80,60,20);
      Ok_btn.addMouseListener(frm);
      Cancel_btn.addMouseListener(frm);
      //Dialog排版
      dlg.setTitle("Set");
      dlg.setBounds(300,250,200,150);
      dlg.setLayout(null);
      for(int i=0;i<4;i++)//將tx[]加入Dialog
      {
         tx[i].setVisible(false);
         dlg.add(tx[i]);
      }
      dlg.add(Ok_btn);//將btn加入Dialog
      dlg.add(Cancel_btn);
   }

   public Graphics g;//繪圖區
   public Color c;//顏色
   public boolean flog=false,flog1=false,flog2=true;//控制開關
   public int x1,x2,y1,y2;//畫筆座標
   public float d=1.0f;//畫筆粗度
   public void mousePressed(MouseEvent e)
   {
      x1 = e.getX();//取得滑鼠按下時的座標，繪圖起點
      y1 = e.getY();
   }
   public void mouseDragged(MouseEvent e)
   {
      x2 = e.getX();//拖曳時的座標
      y2 = e.getY();
      g = getGraphics();
      paint(g);//畫出兩點連線
      x1 = x2;//更新座標
      y1 = y2;
   }
   public void mouseMoved(MouseEvent e){}
   public void mouseReleased(MouseEvent e){}
   public void mouseEntered(MouseEvent e){}
   public void mouseExited(MouseEvent e){}
   //功能表
   public void actionPerformed(ActionEvent e)
   {
      MenuItem a = (MenuItem)e.getSource();//取得觸發事件的物件
      //畫筆顏色
      if(a == mi[0])//black
         c = Color.black;
      else if(a == mi[1])//red
         c = Color.red;
      else if(a == mi[2])//yellow
         c = Color.yellow;
      else if(a == mi[3])//blue
         c = Color.blue;
      else if(a == mi[4])//自定
      {
         dlg.setVisible(true);//顯示dlg
         frm.setEnabled(false);//停止frｍ
         for(int i = 0;i<3;i++)
            tx[i].setVisible(true);
         flog = true;//防止mouseClicked事件錯誤
      }
      //畫筆粗度
      else if(a == mi[5])//s
         d = 1.0f;
      else if(a == mi[6])//m
         d = 5.0f;
      else if(a == mi[7])//l
         d = 8.0f;
      else if(a == mi[8])//自定
      {
         dlg.setVisible(true);//顯示dlg
         frm.setEnabled(false);//停止frｍ
         tx[3].setVisible(true);
         flog1=true;
      }
      //工具
      else if(a == mi[9])//pen
         flog2 = true;
      else if(a == mi[10])//eraser
         flog2 = false;
      else if(a == mi[11])//claen
      {
         d = 1.0f;
         x1 = x2 = y1 = y2 = 0;
         update(g);
      }
   }
   //dlg的button
   public void mouseClicked(MouseEvent e)
   {
      Button btn = (Button)e.getSource();//取得觸發事件的物件
      if(flog)
      {
         if(btn == Ok_btn)//ok
         {
            c = new Color(Integer.parseInt(tx[0].getText()),Integer.parseInt(tx[1].getText()),Integer.parseInt(tx[2].getText()));//自定rgb
            frm.setEnabled(true);//恢復frm
            dlg.setVisible(false);//隱藏dlg
         }
         else if(btn == Cancel_btn)//cancle
         {
            frm.setEnabled(true);//恢復frm
            dlg.setVisible(false);//隱藏dlg
         }
      for(int i = 0;i<3;i++)
         tx[i].setVisible(false);
         flog = false;//防止mouseClicked事件錯誤
      }
      else if(flog1 && Float.parseFloat(tx[3].getText())>0)
      {
         if(btn == Ok_btn)//ok
         {
            d = Float.parseFloat(tx[3].getText());//自定SIZE
            frm.setEnabled(true);//恢復frm
            dlg.setVisible(false);//隱藏dlg
         }
         else if(btn == Cancel_btn)//cancle
         {
            frm.setEnabled(true);//恢復frm
            dlg.setVisible(false);//隱藏dlg
         }
         tx[3].setVisible(false);
         flog1=false;//防止mouseClicked事件錯誤
      }
   }
   //繪圖
   public void paint(Graphics g)
   {
      Graphics2D g2 = (Graphics2D)g;//將Graphics g強制轉換成Graphics2D g2
      if(flog2)//模式轉換
         g2.setColor(c);//筆
      else
         g2.setColor(frm.getBackground());//擦子
      
      g2.setStroke(new BasicStroke(d));//粗細
      g2.drawLine(x1,y1,x2,y2);//畫線
   }
}
