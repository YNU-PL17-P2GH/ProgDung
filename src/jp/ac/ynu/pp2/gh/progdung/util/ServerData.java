package jp.ac.ynu.pp2.gh.progdung.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ServerData {
	public ServerData(){
		
	}
	
	public boolean makeUser(String usrname,String pass){   
		try{
			  File file = new File("./user/"+usrname+".txt");	
			//ユーザ名とパスワードを管理するファイル
			  if(!file.exists()){
				  FileWriter filewriter =new FileWriter(file);
				  filewriter.write(pass);
				  filewriter.close();
				  
				  file = new File("userlist.txt");
				  filewriter = new FileWriter(file,true);
				  filewriter.write(usrname+"\n");
				  filewriter.close();
				  
				  file = new File("./savedata/"+usrname+"objdata.txt");
				  filewriter = new FileWriter(file);
				  filewriter.write("");
				  filewriter.close();
				  //セーブデータを保持するファイル
				  
			  }else{
				  System.out.println("file is exists");
				  return false;
			  }
			  
		}catch(IOException e){
			  System.out.println(e);
		}
		return true;

	}
	
	public boolean checkUser(String userName) {
		return new File("./user/"+userName+".txt").exists();
	}
	
	public boolean queryUser(String usrname,String pass){	//ユーザ名とパスワードを照合する
		try{
			File file= new File("./user/"+usrname+".txt");
			if(file.exists()){
			BufferedReader br = new BufferedReader(new FileReader(file));
			String str = br.readLine();
				System.out.println("  str="+str);
				System.out.println("  pass="+pass);
				br.close();
			    if(str.equals(pass)){
			    	return true;
			    }else{
			    	return false;
			    }
			    
			    
			}else{
				System.out.println("file is not exist");
				return false;
			}
			
			  
		}catch(IOException e){
			  System.out.println(e);
			  return false;
		}
		
	}
	
	public boolean saveData(String usrname,Object saveobj){		//セーブデータをオブジェクトデータで保存する
		FileOutputStream outfile = null;
		ObjectOutputStream outobj=null;
		try {
			outfile = new FileOutputStream("./savedata/"+usrname+"objdata.txt");
			outobj= new ObjectOutputStream(outfile);
			outobj.writeObject(saveobj);
			outfile.close();outobj.close();
			return true;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
			
	}
	
	public Object loadData(String usrname){		//セーブデータのオブジェクトデータを返す
		
		try {
			FileInputStream infile = new FileInputStream("./savedata/"+usrname+"objdata.txt");
			ObjectInputStream inobj;
			inobj = new ObjectInputStream(infile);
			Object obj =inobj.readObject();
			infile.close();inobj.close();
			
			return obj;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		return null;
		
	}
	
	public boolean checkLength(String usrname,String pass){	//ユーザ名とパスワードの長さをチェックする
		int MAXLENGTH=16;int MINLENGTH=2;
		if(usrname.length()>MAXLENGTH || pass.length()>MAXLENGTH || usrname.length()<MINLENGTH || pass.length()<MINLENGTH){
			System.out.println("reject");
			return false;
		}
		
		if(usrname.equals(" ") || pass.equals(" ")){
			return false;
		}
		
		return true;
	}
	
}
