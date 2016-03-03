
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.InputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ivan.miranda
 */
public class SSHService {

    private String ipAddress;

    private int port = 22;

    private SSHUserInfo userInfo;

    private Session session;

    /**
     * Construtor
     *
     * @param userInfo
     * @param ip
     */
    public SSHService(SSHUserInfo userInfo, String ip) {
        this.userInfo = userInfo;
        ipAddress = ip;
    }

    /**
     * Carrega a sessão
     *
     * @throws JSchException em caso de erro de conexão
     */
    public void connect() throws JSchException {
        JSch jsch = new JSch();

        session = jsch.getSession(getUserInfo().getUsuario(), getIpAddress(),
                getPort());
        session.setUserInfo(getUserInfo());
        session.connect();
    }

    /**
     * Executa o comando e retorna a saida que ele gerou
     *
     * @param command
     * @return
     * @throws JSchException caso ocorra erro ao abrir o canal de execução
     * @throws IOException caso ocorra erro ao ler os bytes que ele retornar
     */
    public String exec(String command) throws JSchException, IOException {

        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);
        channel.setInputStream(null);

        InputStream in = channel.getInputStream();

        channel.connect();

        byte[] tmp = new byte[1024];

        StringBuilder resposta = new StringBuilder();
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) {
                    break;
                }
                resposta.append(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                if (in.available() > 0) {
                    continue;
                }
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception ee) {
            }
        }
        channel.disconnect();

        return resposta.toString();
    }

    /**
     * Encerra a conexão
     */
    public void close() {
        session.disconnect();
    }

    // GET E SET
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public SSHUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(SSHUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
