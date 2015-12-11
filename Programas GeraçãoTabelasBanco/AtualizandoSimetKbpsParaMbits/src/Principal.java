import java.sql.ResultSet;
import java.sql.SQLException;


public class Principal {

	public static void main(String[] args) {
	
 //tcpDownload
	 	
		AcessaBanco a = new AcessaBanco();
		AcessaBanco b = new AcessaBanco();
		
		
		a.abrirConexao();
		a.setSql("SELECT simetpraca.id_praca, simetpraca.id_data_hora, "
				+ "simetpraca.tcpDownloadStr, simetpraca.tcpDownloadStrUn "
				+ "FROM `simetpraca` WHERE `tcpDownloadStrUn` LIKE '%k%'");
		
		ResultSet rs = a.consulta();
		try {
			while(rs.next()){
				double tcpDownload;
				tcpDownload = Double.parseDouble(rs.getString(3));
				tcpDownload = tcpDownload/1000;
				b.abrirConexao();
				
				b.setSql("UPDATE `simetpraca` SET `tcpDownloadStr`='"+tcpDownload+"',`tcpDownloadStrUn`=' Mbit/s' WHERE (`id_praca`='"+rs.getString(1)+"') AND (`id_data_hora`='"+rs.getString(2)+"')");  

				b.executa();
				b.fecharConexao();	
				
				
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		a.fecharConexao();
		
		
		
 //tcpUpload
		
		AcessaBanco a1 = new AcessaBanco();
		AcessaBanco b1 = new AcessaBanco();
	
		
		
		a1.abrirConexao();
		a1.setSql("SELECT simetpraca.id_praca, simetpraca.id_data_hora, "
				+ "simetpraca.tcpUploadStr, simetpraca.tcpUploadStrUn "
				+ "FROM `simetpraca` WHERE `tcpUploadStrUn` LIKE '%k%'");
		
		ResultSet rs1 = a1.consulta();
		try {
			while(rs1.next()){
				double tcpUpload;
				tcpUpload = Double.parseDouble(rs1.getString(3));
				tcpUpload = tcpUpload/1000;
				b1.abrirConexao();
				
				b1.setSql("UPDATE `simetpraca` SET `tcpUploadStr`='"+tcpUpload+"',`tcpUploadStrUn`=' Mbit/s' WHERE (`id_praca`='"+rs1.getString(1)+"') AND (`id_data_hora`='"+rs1.getString(2)+"')");  

				b1.executa();
				b1.fecharConexao();	
				
				
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		a1.fecharConexao();
		
	

 // udpDownload
		
		AcessaBanco a2 = new AcessaBanco();
		AcessaBanco b2 = new AcessaBanco();
		
		
		a2.abrirConexao();
		a2.setSql("SELECT simetpraca.id_praca, simetpraca.id_data_hora, "
				+ "simetpraca.udpDownloadStr, simetpraca.udpDownloadStrUn "
				+ "FROM `simetpraca` WHERE `udpDownloadStrUn` LIKE '%k%'");
		
		ResultSet rs2 = a2.consulta();
		try {
			while(rs2.next()){
				double udpDownload;
				udpDownload = Double.parseDouble(rs2.getString(3));
				udpDownload = udpDownload/1000;
				b2.abrirConexao();
				
				b2.setSql("UPDATE `simetpraca` SET `udpDownloadStr`='"+udpDownload+"',`udpDownloadStrUn`=' Mbit/s' WHERE (`id_praca`='"+rs2.getString(1)+"') AND (`id_data_hora`='"+rs2.getString(2)+"')");  

				b2.executa();
				b2.fecharConexao();				
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		a2.fecharConexao();
		
		
		

//udpUpload		
		
		AcessaBanco a3 = new AcessaBanco();
		AcessaBanco b3 = new AcessaBanco();
	
		
		
		a3.abrirConexao();
		a3.setSql("SELECT simetpraca.id_praca, simetpraca.id_data_hora, "
				+ "simetpraca.udpUploadStr, simetpraca.udpUploadStrUn "
				+ "FROM `simetpraca` WHERE `udpUploadStrUn` LIKE '%k%'");
		
		ResultSet rs3 = a3.consulta();
		try {
			while(rs3.next()){
				double udpUpload;
				udpUpload = Double.parseDouble(rs3.getString(3));
				udpUpload = udpUpload/1000;
				b3.abrirConexao();
				
				b3.setSql("UPDATE `simetpraca` SET `udpUploadStr`='"+udpUpload+"',`udpUploadStrUn`=' Mbit/s' WHERE (`id_praca`='"+rs3.getString(1)+"') AND (`id_data_hora`='"+rs3.getString(2)+"')");  

				b3.executa();
				b3.fecharConexao();					
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		a3.fecharConexao();
		
		
		System.out.println("Done");
		
	}

}
