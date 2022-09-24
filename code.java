import java.io.*;
import java.lang.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
class cmdprmpt_new extends Frame implements KeyListener,TextListener, WindowListener
{
	TextArea ta;
	String s1,pl,wholeText;
	int l1;
	Thread th;
	String sub="";
	File f;
	String Text="";
	cmdprmpt_new()
	{
		Frame f=new Frame();
		setSize(1150,500);
		setVisible(true);
		setEnabled(true);
		setTitle("Command Prompt");
		setLayout(new BorderLayout());
		s1="C:/Users/CG-DTE>";
		wholeText=s1;
		pl="C:/Users/CG-DTE/";
		l=pl.length();
		ta=new TextArea(s1);
		ta.setBackground(Color.black);
		ta.setForeground(Color.white);
		ta.setFont(new Font("Lucida Console",Font.PLAIN,15));		
		add(ta,BorderLayout.CENTER);
		ta.addKeyListener(this);
		//ta.addTextListener(this);
		this.addWindowListener(this);
		ta.setCaretPosition(ta.getText().length());
		show();
		                                                                                                                                                                                                                                                                                                                       
	}
	String s;
	String sub2="";
	int l;
	int keychar=0;
	int keycount=0;
	public void textValueChanged(TextEvent te)
	{
		
	}
	public void keyPressed(KeyEvent ke)
	{
		int code=ke.getKeyCode();
		typedText+=(code>=65&&code<=90)||(code>=97&&code<=103)||(code>=45&&code<=59)||code==32?ke.getKeyChar():"";
	}
	String typedText="";
	String errormsg="System is not reconizing the command :[ CMD ]";
	public void keyReleased(KeyEvent ke)
	{
		int code=ke.getKeyCode();
		
		//System.out.println(code);
		
		if(code==8)
		{
			if(typedText.length()!=0)
				typedText=typedText.substring(0,typedText.length()-1);
			else
			{
				ta.setText(wholeText);
			}
			ta.setCaretPosition(ta.getText().length());
		}
		 else if(code==10)
		{
			System.out.println("...."+typedText);

			typedText=typedText.trim();
			if(single_char())
			{
				if(typedText.length()==1)
					ta.append("\n"+errormsg.replace("CMD",typedText)+"\n"+s1);
				else
					ta.append(pl.substring(0,pl.length()-1)+">");
			}
			else if(drive_changed())
			{
				File f=new File(typedText);
				if (f.isDirectory()&&typedText.charAt(0)!=67&&typedText.charAt(0)!=99)
				{	
					ta.append(typedText+"/>");
					pl=typedText+"/";
				}
				else if(f.isDirectory()&&typedText.charAt(0)==67||typedText.charAt(0)==99)
				{
					ta.append(s1);
					pl="C:/Users/CG-DTE/";
				}
				else
				{
					ta.append("The system cannot find the drive specified.\n"+pl.substring(0,pl.length()-1)+">");
				}
			}
			else if(entered_into_folder())
			{
				String folder=typedText.substring(3);
				File f=new File(pl+folder);
				if(f.isDirectory()||f.isFile())
				{
					ta.append("\n"+pl+folder+">");
					pl=pl+folder+"/";
					//System.out.println();
				}
				else
				{
					ta.append("\n The system cannot find the path specified.\n"+pl+">");
				}
			}
			else if(entered_into_same_folder())
			{
				ta.append(pl.substring(0,pl.length()-1));
				ta.append("\n\n"+pl.substring(0,pl.length()-1)+">");
			}
			else if(returned_to_folder())
			{
				int x=0;
				for(int i=pl.length()-2;i>=0;i--)
				{
					if(pl.charAt(i)==47)
					{
						x=i;
						break;
					}
				}
				if(x!=pl.length()-2&&pl.length()>3)
				{
					pl=pl.substring(0,x)+"/";
					if(pl.length()==3)
						ta.append(pl+">");
					else
						ta.append(pl=pl.substring(0,pl.length()-1)+">");
				}
				else
				{
					ta.append(pl+">");
				}
				
			}		
			else if(clear_screen())
			{
				ta.setText(pl+">");
			}
			else if(return_to_rootdir())
			{
				pl=pl.substring(0,3);
				ta.append(pl+">");
			}
			else if(dir_comand())
			{
				ta.append("directory of "+pl.substring(0,pl.length()-1));
				File file=new File(pl.substring(0,pl.length()-1));
				File fl[]=file.listFiles();
				String st[]=file.list();
				int l=fl.length;
				int Year;
				long ll=0;
				int noOfDir=0;
				int noOfFiles=0;
				String a[]=new String[3];
				for(int i=0;i<l;i++)
				{
					Date date=new Date(fl[i].lastModified());
					if(fl[i].canRead())
						a[0]="R";
					else
						a[0]="NR";
					if(fl[i].canWrite())
						a[1]="W";
					else
						a[1]="NW";
					if(fl[i].canExecute())
						a[2]="E";
					else
						a[2]="NE";
					if(fl[i].isFile())
					{
						Year=1900+date.getYear();
						ta.append("\n"+date.getDate()+"-"+date.getMonth()+"-"+Year+"	"+date.getHours()+":"+date.getMinutes()+"     		             "+fl[i].length()+"	           "+st[i]+"	"+a[0]+" , "+a[1]+" , "+a[2]);
						noOfFiles=noOfFiles+1;
						ll=ll+fl[i].length();	
					}
					else if(fl[i].isDirectory())
					{
						Year=1900+date.getYear();
						ta.append("\n"+date.getDate()+"-"+date.getMonth()+"-"+Year+"	"+date.getHours()+":"+date.getMinutes()+"             <DIR>            	"+fl[i].length()+" 	         "+st[i]);
						noOfDir=noOfDir+1;
					}
					Year=0;
				}
				ta.append("\n	       "+noOfFiles+"File(s)	     "+ll+"         long");
				ta.append("\n	       "+noOfDir+"dir(s)	     "+file.getFreeSpace()+"       long Free");
				ta.append("\n"+pl.substring(0,pl.length()-1)+">");
			}
			else if(creating_folder())
			{
				if(typedText.length()==5)
				{
					ta.append("The syntax of the command is incorrect.");
					ta.append("\n\n"+pl.substring(0,pl.length()-1)+">");
				}
				else
				{
					ta.append("\n"+pl.substring(0,pl.length()-1)+">");
				}
			}
			else
			{
				ta.append(errormsg.replace("CMD",typedText)+"\n"+s1);
			}
			typedText="";
			wholeText=ta.getText(); 
		}
		 else if(code==9)
		{
		}
		ta.setCaretPosition(ta.getText().length());
	}
	public void keyTyped(KeyEvent ke)
	{
	}
	boolean single_char()
	{
		if(typedText.length()==0||typedText.length()==1)
			return true;
		else
			return false;
	}
	boolean drive_changed()
	{
		if(typedText.length()==2&&(typedText.charAt(0)>=65&&typedText.charAt(0)<=90)||(typedText.charAt(0)>=97&&typedText.charAt(0)<=103)&&typedText.charAt(1)==58)
			return true;
		else
			return false;
	}	
	boolean entered_into_folder()
	{
		if((typedText.charAt(0)==67||typedText.charAt(0)==99)&&(typedText.charAt(1)==68||typedText.charAt(1)==100)&&typedText.charAt(2)==32&&typedText.length()>=3)	
			return true;
		else
			return false ;
	}
	boolean entered_into_same_folder()
	{
		if((typedText.charAt(0)==67||typedText.charAt(0)==99)&&(typedText.charAt(1)==68||typedText.charAt(1)==100)&&typedText.length()==2)
		{	
			return true;
		}
		else
			return false ;
	}
	boolean clear_screen()
	{
		if((typedText.charAt(0)==67||typedText.charAt(0)==99)&&(typedText.charAt(1)==76||typedText.charAt(1)==108)&&(typedText.charAt(2)==83||typedText.charAt(2)==115)&&typedText.length()==3)
		{	
			return true;
		}
		else
			return false ;
	}
	boolean returned_to_folder()
	{
		if((typedText.charAt(0)==67||typedText.charAt(0)==99)&&(typedText.charAt(1)==68||typedText.charAt(1)==100)&&typedText.charAt(2)==46&&typedText.charAt(3)==46&&typedText.length()==4)	
			return true;
		else
			return false ;
	}
	boolean return_to_rootdir()
	{
		if((typedText.charAt(0)==67||typedText.charAt(0)==99)&&(typedText.charAt(1)==68||typedText.charAt(1)==100)&&typedText.charAt(2)==47&&typedText.length()==3)	
			return true;
		else
			return false ;
	}
	boolean dir_comand()
	{
		if((typedText.charAt(0)==68||typedText.charAt(0)==100)&&(typedText.charAt(1)==73||typedText.charAt(1)==105)&&(typedText.charAt(2)==82||typedText.charAt(2)==114)&&typedText.length()==3)
		{
			return true;
		}
		else
			return false;
	}
	boolean creating_folder()
	{
		if((typedText.charAt(0)==77||typedText.charAt(0)==109)&&(typedText.charAt(1)==75||typedText.charAt(1)==107)&&(typedText.charAt(2)==68||typedText.charAt(2)==100)&&(typedText.charAt(3)==73||typedText.charAt(3)==105)&&(typedText.charAt(4)==82||typedText.charAt(4)==114)&&typedText.length()>=5)	
			return true;
		else
			return false ;
	}	
	public void windowActivated(WindowEvent we)
	{
	}
	public void windowClosing(WindowEvent we)
	{
		System.exit(0);	
	}
	public void windowClosed(WindowEvent we)
	{
	}
	public void windowDeactivated(WindowEvent we)
	{
	}
	public void windowIconified(WindowEvent we)
	{	
	}
	public void windowDeiconified(WindowEvent we)
	{
	}
	public void windowOpened(WindowEvent we)
	{
	}
	public static void main( String o[])
	{	
		new cmdprmpt_new();
	}
}
