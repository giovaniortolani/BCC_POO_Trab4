
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.net.*;
import java.io.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class ServerConection {
	
	private static ArrayList<ClientConection> client;
	static Scanner reader = new Scanner(System.in);
	private static ServerSocket listener;
	private static ArrayList<Cadastro> cadastros;
	private static ArrayList<Estoque> estoques;
	private static String resp;
	/*******************SINGLETON PATTERN*********************/
	public static ServerAndClient instance = null; 
	
	public static synchronized ServerAndClient getInstance() { 
		if (instance == null) 
			instance = new ServerAndClient(client, listener,cadastros,estoques); 
		return instance; 
	}
	/*********************************************************/
	
    public static void main(String[] args) throws IOException {
		client = new ArrayList<ClientConection>();

		try {
			listener = new ServerSocket(12345);
		} catch(IOException ioe) { 
            System.out.println("Erro na porta");
            System.exit(0);
		}
        System.out.println("Server is Running");
        
        cadastros = new ArrayList<Cadastro>();
		estoques = new ArrayList<Estoque>();
		CarregaCadastros();
		CarregaEstoque();
		
		if (getInstance() != null) {
			Thread serverr = new Thread(instance);
			serverr.start();		
		}
		else {
			System.out.println("Conexao já inicializada ");
		}
		
		do {
			System.out.printf("\n\t\t\tSUPERMERCADO SERVER");
			System.out.printf("\n1-Cadastro e atualizacao de produtos");
			System.out.printf("\n2-Listar produtos em estoque");
			System.out.printf("\n3-Listar produtos esgotados");
			System.out.printf("\n0-Sair\n");
			resp = reader.nextLine();
			if (resp.equals("1")) 
				CadastrarProdutos();
			if (resp.equals("2"))
				ListarProdutosEstoque();
			if (resp.equals("3"))
				ListarEsgotados();
		} while (!resp.equals("0"));
		GravaArquivo("cadastro.csv");
		GravaArquivo("estoque.csv");
		System.exit(0);
    }
    /***************************************************************************************************/
    static void CadastrarProdutos(){
    	System.out.printf("\nDigite o codigo do produto: ");
		String aux = reader.nextLine();
		for (Estoque e: estoques) {
			if (e.getCodigo().equals(aux)) { //Atualiza o estoque do produto
				System.out.println("\n"+e.Imprime());
				System.out.println("Digite a quantidade que sera inserida no estoque: ");
				aux = reader.nextLine();
				e.setQuantidade(e.getQuantidade()+Integer.parseInt(aux));
				System.out.println("Produto atualizado com sucesso. ");
				System.out.println("Deseja enviar email para os clientes cadastrados(s/n)? ");
				aux = reader.nextLine();
				if (aux.equals("s"))
					EnviarEmail();
				return;
			}
		}
		resp = aux+",";
		System.out.printf("Digite o nome do produto: ");
		resp = resp + reader.nextLine()+",";
		System.out.printf("Digite o preco do produto: ");
		try {
			resp = resp + Double.parseDouble(reader.nextLine())+",";
		}
		catch (Exception e){
			System.out.println("Preço invalido!");
			return;
		}
		System.out.printf("Digite a validade do produto(dd/MM/yyyy): ");
		try {
			Date Data = new Date();
			aux = reader.nextLine();
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");    
			Data = df.parse(aux);  
			resp = resp + df.format(Data)+",";
		} 
		catch (Exception ex) {  
	        System.out.println("Data invalida!");
	        return;
	    }  
		System.out.printf("Digite o fornecedor do produto: ");
		resp = resp + reader.nextLine()+",";
		System.out.printf("Digite a quantidade a ser estocada: ");
		resp = resp + Integer.parseInt(reader.nextLine());
		estoques.add(new Estoque(resp));
    }
    /***************************************************************************************************/
    static void ListarProdutosEstoque(){
    	System.out.println("oi");
    	for (Estoque e: estoques) {
    		if (e.getQuantidade()!= 0)
    			System.out.println(e.Imprime());
    	}
    }
    /***************************************************************************************************/
    static void ListarEsgotados(){
    	for (Estoque e: estoques) {
    		if (e.getQuantidade()== 0)
    			System.out.println(e.Imprime());
    	}
    }
    /***************************************************************************************************/
    static void EnviarEmail(){
    	Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

	    try{
	       for (Cadastro c: cadastros){
	    	   String msgBody = "Olá "+c.getNome()+" nós carregamos nosso estoque. Venha conferir! ";
	    	   Message msg = new MimeMessage(session);
	           msg.setFrom(new InternetAddress("julia.macias@usp.br", "SuperMercado"));
	           msg.addRecipient(Message.RecipientType.TO,new InternetAddress(c.getEmail(), "Caro Cliente, "));
	           msg.setSubject("Atualizamos nosso estoque para voce! ");
	           msg.setText(msgBody);
	           Transport.send(msg);
	       }
	       System.out.println("Emails enviados com sucesso!");
	    }catch (MessagingException mex) {
	       mex.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /***************************************************************************************************/
    static void CarregaCadastros(){
		try {
			BufferedReader in = new BufferedReader(new FileReader("cadastro.csv"));
			String csv=in.readLine();
			if (csv.equals("")) {
				System.out.println("Arquivo de cadastro vazio");
				System.exit(0);
			}
			do {
				cadastros.add(new Cadastro(csv));
			} while((csv = in.readLine()) != null);
			in.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Arquivo cadastro was not found!");
		}
		catch(IOException e) {
			System.out.println("Error reading the file!");
		}
	}
    /***************************************************************************************************/
	static void CarregaEstoque(){
		try {
			BufferedReader in = new BufferedReader(new FileReader("estoque.csv"));
			String csv=in.readLine();
			if (csv.equals("")) {
				System.out.println("Arquivo de estoque vazio");
				System.exit(0);
			}
			do {
				estoques.add(new Estoque(csv));
			} while((csv = in.readLine()) != null);
			in.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Arquivo estoque was not found!");
		}
		catch(IOException e) {
			System.out.println("Error reading the file!");
		}
	}
	/***************************************************************************************************/	
	static void GravaArquivo(String file) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			if (file.equals("cadastro.csv")) {
				for (Cadastro l:cadastros)
					out.write(l.getNome()+","+l.getEndereco()+","+l.getTel()+","+l.getEmail()+","+l.getID()+","+l.getSenha()+"\n");
			}
			if (file.equals("estoque.csv")){
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				for (Estoque b:estoques)
					out.write(b.getCodigo()+","+b.getNome()+","+b.getPreco()+","+df.format(b.getVal())+","+b.getFornecedor()+","+b.getQuantidade()+"\n");
			}
			out.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Arquivo " + file + " was not found!");
		}
		catch(IOException e) {
			System.out.println("Error reading the file!");
		}
	}
}

class ServerAndClient implements Runnable {
	
	private ArrayList<ClientConection> client;
	private ServerSocket listener;
	private static ArrayList<Cadastro> cadastros;
	private static ArrayList<Estoque> estoques;

	public ServerAndClient(ArrayList<ClientConection> cl,ServerSocket lis,ArrayList<Cadastro> cad,ArrayList<Estoque> est){
		this.client = cl;
		this.listener = lis;
		cadastros = cad;
		estoques = est;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
            while (true) {
            	Socket Csocket = this.listener.accept();
				ClientConection clieCon = new ClientConection(Csocket,cadastros,estoques);
				this.client.add(clieCon);
				Thread thr = new Thread(clieCon);
				thr.start();
				System.out.println("Cliente conectado");
            }
        }
        catch(IOException error){
        	System.out.println("Erro nos clientes");
            System.exit(1);
        }
	}
}