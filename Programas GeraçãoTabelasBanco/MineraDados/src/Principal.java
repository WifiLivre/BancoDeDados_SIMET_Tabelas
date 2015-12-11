import java.sql.ResultSet;
import java.sql.SQLException;

/*criação dos dados da tabela latenciaUsuarios
 * necessario dados das tabelas:
 * praca
 * dadospraca
 * simetpraca
 * 
 * 
 * verificar hora de inicio int i no for e..
 * verificar mes
 */

public class Principal {

	public static void main(String[] args) {
		
		String mesIns = "11";
		
		AcessaBanco a1 = new AcessaBanco();
		AcessaBanco a5 = new AcessaBanco();
		AcessaBanco a2 = new AcessaBanco();	
		AcessaBanco a3 = new AcessaBanco();
		
		a5.abrirConexao();
		a3.abrirConexao();
		a2.abrirConexao();
		a1.abrirConexao();
		
		System.out.println("idPraca"+" | "+"numMaxUsuarios"+" | "+"numeroDeUsuarios"+" | "+"tcpDownload "
				+" | "+"tcpDownloadUn"+" | "+"latencia"+" |     "+"dataHora");
		
		for(int i = 18; i < 25; i++){
			String hora = null;	
			if(i < 10){
				hora = "0"+ String.valueOf(i);
			}else{
				hora = String.valueOf(i);
			}
			
			
			int idpraca = 0;
			int numMaxUsuarios = 0;
			a1.setSql("SELECT praca.id_praca, praca.n_max_usuarios FROM praca ORDER BY praca.id_praca ASC");
		
			ResultSet rs1 = a1.consulta();
			try {
				while(rs1.next()){
					idpraca = Integer.parseInt(rs1.getString(1));
					if(!(rs1.getString(2) == null)){			
	
						
						numMaxUsuarios = Integer.parseInt(rs1.getString(2));
						//System.out.println(idpraca+"|"+numMaxUsuarios);
					
						
					
						a2.setSql("SELECT"
								+ " praca.id_praca, "
								+ "dados_praca.usuarios, "
								+ "dados_praca.id_data_hora, "
								+ "dados_praca.entrada "
								+ "FROM "
								+ "praca, "
								+ "dados_praca "
								+ "WHERE "
								+ "praca.id_praca = dados_praca.id_praca AND "
								+ "dados_praca.id_data_hora LIKE '2015-"+mesIns+"-% "+hora+":%' AND "
								+ "dados_praca.usuarios IS NOT NULL AND "
								+ "praca.id_praca =  '"+idpraca+"'");
					
						//System.out.println(a2.getSql());
					
									
						a3.setSql("SELECT "
								+ "praca.id_praca, "
								+ "simetpraca.latenciaStr, "
								+ "simetpraca.id_data_hora "
								+ "FROM "
								+ "simetpraca, "
								+ "praca "
								+ "WHERE "
								+ "simetpraca.latenciaStr IS NOT NULL  AND "
								+ "simetpraca.id_praca =  praca.id_praca AND "
								+ "simetpraca.id_data_hora LIKE  '2015-"+mesIns+"-% "+hora+":%' AND "
								+ "praca.id_praca =  '"+idpraca+"'");
	
					
						//System.out.println(a3.getSql());
						
								
						
				
						ResultSet rs2 = a2.consulta();
						
					
						ResultSet rs3 = a3.consulta();
						
						
						try {
							while(rs2.next()){
								rs3.beforeFirst();
								while(rs3.next()){
									if((Integer.parseInt((rs3.getString(3).substring(8,10))) == (Integer.parseInt((rs2.getString(3).substring(8,10)))))){
									//	System.out.println(idpraca+" | "+rs2.getString(2)+" | "+rs3.getString(2)+" |     "+rs2.getString(3) +" | "+ rs3.getString(3));
										System.out.println(idpraca+" | "+numMaxUsuarios+" | "+rs2.getString(2)+" | "+rs2.getString(4)+" | "+"Kbits"+" | "+rs3.getString(2)+" |     "+rs2.getString(3));
									
										if(rs2.getString(4) == null){
											a5.setSql("INSERT INTO `latenciaUsuarios` "
												+ "(`id_praca`,`numeroMaxUsuarios`,`numeroUsuarios` "
												+ ",`latencia`, "
												+ "`id_data`,`id_hora`) VALUES "
												+ "('"+ idpraca +"','"+numMaxUsuarios+"','"+rs2.getString(2)+"','"+rs3.getString(2)+"','"+rs2.getString(3).substring(0, 11)+"','"+rs2.getString(3).substring(11,16)+":00"+"')");
										}else{
											double taxa;
											taxa = Double.parseDouble(rs2.getString(4))/1000;
											
											a5.setSql("INSERT INTO `latenciaUsuarios` "
													+ "(`id_praca`,`numeroMaxUsuarios`,`numeroUsuarios` "
													+ ",`tcpDownload`,`tcpDownloadUn`,`latencia`, "
													+ "`id_data`,`id_hora`) VALUES "
													+ "('"+ idpraca +"','"+numMaxUsuarios+"','"+rs2.getString(2)+"','"+taxa+"','Mbits','"+rs3.getString(2)+"','"+rs2.getString(3).substring(0,11)+"','"+rs2.getString(3).substring(11,16)+":00"+"')");
										
										}
										System.out.println(a5.getSql());
										a5.executa();
									
										
										
										
										break;
									}
								}
								
								
								
								
							//	System.out.println(rs2.getString(1)+"|"+rs2.getString(2)+"|"+rs2.getString(3));
							
							
							
							
							
							
							}
						} catch (SQLException e) {
				
							e.printStackTrace();
						}
					
						
						
						
					
					
						//break;
					
					}			
				}
			} catch (SQLException e) {
	
				e.printStackTrace();
			}

		
		}
		
		
		
		
		
		
		/*
		
		
		a.setSql("SELECT"
				+ " praca.id_praca, "
				+ "dados_praca.usuarios, "
				+ "dados_praca.id_data_hora "
				+ "FROM "
				+ "praca, "
				+ "dados_praca "
				+ "WHERE "
				+ "praca.id_praca = dados_praca.id_praca AND "
				+ "dados_praca.id_data_hora LIKE '2014-10-% 01:%' AND "
				+ "dados_praca.usuarios IS NOT NULL");
		System.out.println(a.getSql());
		a.abrirConexao();
		ResultSet rs = a.consulta();
		try {
			while(rs.next()){
				System.out.println(rs.getString(1)+"|"+rs.getString(2)+"|"+rs.getString(3));
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		a.fecharConexao();
		
		
		*/
		
		a1.fecharConexao();
		a5.fecharConexao();
		a3.fecharConexao();
		a2.fecharConexao();
		
		System.out.println("FIM");
		
	}

}
