
import com.jcraft.jsch.UserInfo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 *
 * @author ivan.miranda
 */
public class SSHUserInfo implements UserInfo{

	private final String senha;
	private final String usuario;

	public SSHUserInfo(String usuario, String senha){
		this.usuario = usuario;
		this.senha = senha;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	public String getUsuario(){
		return usuario;
	}

	@Override
	public String getPassphrase() {
		return null;
	}

	@Override
	public boolean promptPassphrase(String arg0) {
		return true;
	}

	@Override
	public boolean promptPassword(String arg0) {
		return true;
	}

	@Override
	public boolean promptYesNo(String arg0) {
		return true;
	}

	@Override
	public void showMessage(String arg0) {
	}

}