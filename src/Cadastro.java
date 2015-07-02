
public class Cadastro{
	private String nome;
	private String endereco;
	private String telefone;
	private String email;
	private String ID;
	private String senha;
	
	public Cadastro(String csv){
		String[] values = csv.split(",");
		this.nome = values[0];
		this.endereco = values[1];
		this.telefone = values[2];
		this.email = values[3];
		this.ID = values[4];
		this.senha = values[5];
	}
	/*************************************************************************/
	void setNome(String nome) {
		this.nome = nome;
	}
	
	String getNome() {
		return this.nome;
	}
	
	void setEndereco(String end) {
		this.endereco = end;
	}
	
	String getEndereco() {
		return this.endereco;
	}
	
	void setTel(String tel) {
		this.telefone = tel;
	}
	
	String getTel() {
		return this.telefone;
	}
	
	void setEmail(String email) {
		this.email = email;
	}
	
	String getEmail() {
		return this.email;
	}
	
	void setID(String ID) {
		this.ID = ID;
	}
	
	String getID() {
		return this.ID;
	}
	
	void setSenha(String senha) {
		this.senha = senha;
	}
	
	String getSenha() {
		return this.senha;
	}
}