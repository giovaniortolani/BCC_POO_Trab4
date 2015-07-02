import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Estoque{

	private String codigo;
	private String nome;
	private Double preco;
	private Date validade;
	private String fornecedor;
	private int quantidade;
	
	public Estoque(String csv){
		String[] values = csv.split(",");
		this.codigo = values[0];
		this.nome = values[1];
		this.preco = Double.parseDouble(values[2]);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");    
		try {
			validade = df.parse(values[3]); 
		}
		catch (Exception ex) {  
	        ex.printStackTrace();  
	    }
		this.fornecedor = values[4];
		this.quantidade = Integer.parseInt(values[5]);
	}
	/*************************************************************************/
	void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	String getCodigo() {
		return this.codigo;
	}
	
	void setNome(String nome) {
		this.nome = nome;
	}
	
	String getNome() {
		return this.nome;
	}
	
	void setPreco(Double preco) {
		this.preco = preco;
	}
	
	Double getPreco() {
		return this.preco;
	}
	
	void setVal(Date Val) {
		this.validade = Val;
	}
	
	Date getVal() {
		return this.validade;
	}
	
	void setFornecedor(String forn) {
		this.fornecedor = forn;
	}
	
	String getFornecedor() {
		return this.fornecedor;
	}
	
	void setQuantidade(int quant) {
		this.quantidade = quant;
	}
	
	int getQuantidade() {
		return this.quantidade;
	}

	public String Imprime(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");    	
		return "Codigo: "+this.codigo+"\nNome: "+this.nome+"\nPreco: "+this.preco+"\nValidade: "+df.format(this.validade)+"\nFornecedor: "+this.fornecedor+"\nQuantidade: "+this.quantidade+"\n";

	}
}