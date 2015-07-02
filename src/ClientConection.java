

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;


public class ClientConection implements Runnable {
	private DataInputStream in;
	private DataOutputStream out;
	private static List<Cadastro> cadastros;
	private static List<Estoque> estoques;
	/***************************FACTORY METHOD***********************/
	public ClientConection MakeClientConection(Socket socket,List<Cadastro> cad,List<Estoque> est){
		return new ClientConection(socket,cad,est);
	}
	/***************************************************************/
	public ClientConection(Socket socket,List<Cadastro> cad,List<Estoque> est) {
		
		try {
			this.in = new DataInputStream((socket.getInputStream()));
			this.out = new DataOutputStream(socket.getOutputStream());
		} 
		catch(IOException e) {
			
			System.out.println("Error");
		}
		cadastros = cad;
		estoques = est;
	}
	
	@Override
	public void run() {
		try {
			while(true){
				String line;
				while((line = in.readUTF()) != null){
					String[] values = line.split(",");
					/*****************************************************/
					if (values[0].equals("add")) {
						//Cadastra novo usuario
						cadastros.add(new Cadastro(values[1]+","+values[2]+","+values[3]+","+values[4]+","+values[5]+","+values[6]));
					}
					/*****************************************************/
					if (values[0].equals("login")) {
						int cont=0;
						for (Cadastro c: cadastros) {
							if (c.getID().equals(values[1]) && c.getSenha().equals(values[2]))
								out.writeUTF("ok");
							else
								cont++;
						}
						if (cont == cadastros.size()) //Nao encontrou o usuario
							out.writeUTF("error");
					}
					/*****************************************************/
					if (values[0].equals("dispo")) {
						String aux = "";
						for (Estoque e: estoques){
							if (e.getQuantidade()!=0)
								aux= aux+ e.Imprime()+"\n";
						}
						if (aux.isEmpty())
							out.writeUTF("Nenhum produto disponivel");
						else
							out.writeUTF(aux);
					}
					/*****************************************************/
					if (values[0].equals("indis")) {
						String aux="";
						for (Estoque e: estoques){
							if (e.getQuantidade()==0)
								aux= aux+ e.Imprime()+"\n";
						}
						if (aux.isEmpty())
							out.writeUTF("Nenhum produto indisponivel");
						else
							out.writeUTF(aux);
					}
					/*****************************************************/
					if (values[0].equals("compra")){
						int cont=0;
						for (Estoque e: estoques){
							if (e.getCodigo().equals(values[1])) {
								if (e.getQuantidade() == 0)
									out.writeUTF("indis");
								else
									out.writeUTF(e.getNome()+","+e.getQuantidade());
							}
							else
								cont ++;
						}
						if (cont == estoques.size())
							out.writeUTF("NaoExiste");
					}
					/*****************************************************/
					if (values[0].equals("compra2")){
						for (Estoque e: estoques){
							if(e.getCodigo().equals(values[1]))
								e.setQuantidade(e.getQuantidade() - Integer.parseInt(values[2]));
						}
					}
					/*****************************************************/
				}
			}
		} catch (Exception e) {
			System.err.println("Cliente Desconectado");
		}
		
	}

}