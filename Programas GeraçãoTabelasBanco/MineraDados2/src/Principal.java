import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

/*criação dos dados da tabela percentual
 * necessario dados das tabelas:
 * latenciausuarios
 * modificar latenciausuarios.id_praca > -1 caso 900 para erro de conexão
*/
public class Principal {

	public static void main(String[] args) {
		
		AcessaBanco a3 = new AcessaBanco(); 
		a3.setSql("SELECT DISTINCT latenciausuarios.id_praca, latenciausuarios.numeroMaxUsuarios FROM latenciausuarios WHERE latenciausuarios.id_praca > 49");
		a3.abrirConexao();
		ResultSet rs3 = a3.consulta();
		try {
			boolean teste;
			do{
				teste = rs3.next();
			
			
			
				int idpraca = Integer.parseInt(rs3.getString(1));
			
				int numMaxUsuarios = Integer.parseInt(rs3.getString(2));
				System.out.println(idpraca+"|"+numMaxUsuarios);
				
				AcessaBanco a1 = new AcessaBanco();
				AcessaBanco a2 = new AcessaBanco();
				
				DecimalFormat df = new DecimalFormat("##.#");
				double i,min,max,metrica;
				String med;
			
				metrica = 0.05*numMaxUsuarios;
				i=0;
				
				while(i < numMaxUsuarios*3){
					boolean aux = false;
					i=i+metrica;
					min = i-metrica;
					med = df.format((i/numMaxUsuarios)*100);
					max = i+metrica;
					
				//	System.out.println(min +" | "+ max);
					
					a1.setSql("SELECT "
							+ "Avg(latenciausuarios.numeroUsuarios), "
							+ "Avg(latenciausuarios.tcpDownload), "
							+ "Avg(latenciausuarios.latencia) "
							+ "FROM "
							+ "latenciausuarios "
							+ "WHERE "
							+ "latenciausuarios.numeroUsuarios BETWEEN  '"+min+"' AND '"+max+"' AND "
							+ "latenciausuarios.numeroMaxUsuarios = '"+numMaxUsuarios+"' AND "
							+ "latenciausuarios.id_praca =  '"+idpraca+"'");
					a1.abrirConexao();
					ResultSet rs1 = a1.consulta();
					try {
						while(rs1.next()){
							if((rs1.getString(1) == null)||(rs1.getString(2) == null)||(rs1.getString(3) == null)){
								aux = true;
							//	break;						
							}
							System.out.println(idpraca+"|"+med+" | "+rs1.getString(1)+" | "+rs1.getString(2)+" | "+rs1.getString(3));
							a2.abrirConexao();
							a2.setSql("INSERT INTO `percentual` (`id_praca`,`porcentagem`,`numeroUsuarios` "
									+ ",`tcpDownload`,`tcpDownloadUn`,`latencia`) VALUES "
									+ "('"+idpraca+"','"+med+"','"+rs1.getString(1)+"','"+rs1.getString(2)+"','Mbits','"+rs1.getString(3)+"')");
							a2.executa();
							a2.fecharConexao();
							
							rs1.close();
							break;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally{			
						a1.setSql(null);				
						a1.fecharConexao();				
					}			
					if(aux){
						//break;
					}
					
			
				}
			
			}while(teste);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
		
	}

}
