import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;


public class Principal {

	public static void main(String[] args) {
		AcessaBanco a = new AcessaBanco();
		a.abrirConexao();
		
		AcessaBanco ab = new AcessaBanco();
		AcessaBanco ac = new AcessaBanco();
		ac.abrirConexao();
		
		int numRes = -1;
		
		
		
		
		ab.setSql("SELECT "
				+ "Count(dados_praca.id_data_hora) "
				+ "FROM "
				+ "dados_praca "
				+ "WHERE "
				+ "dados_praca.id_praca =  '1001' AND "
				+ "dados_praca.id_data_hora BETWEEN  '2015-07-31 23:30:00' AND '2015-11-01 00:30:00'");
		
		ab.abrirConexao();
		ResultSet rs1 = ab.consulta();
		
		try {
			while(rs1.next()){
				numRes = Integer.parseInt(rs1.getString(1));
			}
		} catch (SQLException e1) {
		
			e1.printStackTrace();
		}
		ab.fecharConexao();
		
		
		System.out.println(numRes+"\n\n\n");
		
		//==============================================================================
		
		String dataHoraIni = "2015-07-31 23:30:00";
		String dataHoraFim = "2015-11-01 00:30:00";

		String horaCerta = null;
		
		String eam,emd,edh; //tipos de espaços m(mês dia, ano mes, dia hora)
		int ano, mes, dia, hora;
		
		String numUs = null;
		String numEn = null;
		String numSa = null;
		
		ano = 2015;
		eam = "-";
		mes = 07;
		emd = "-";
		dia = 31;
		edh = " ";
		hora = 23;
		
		
		String id_praca= "1001";	
		
		
		
		ab.setSql("SELECT DISTINCT "
				+ "dados_praca.id_praca "
				+ "FROM "
				+ "dados_praca "
				+ "WHERE "
				+ "dados_praca.id_data_hora <  '2015-11-01 00:00:09' AND "
				+ "dados_praca.id_praca BETWEEN  '59' AND '70'");
		
		ab.abrirConexao();
		ResultSet rs3 = ab.consulta();
		try {
			while(rs3.next()){
				
				
				ano = 2015;
				eam = "-";
				mes = 07;
				emd = "-";
				dia = 31;
				edh = " ";
				hora = 23;
				
				
				
				
				
				id_praca = rs3.getString(1);
				System.out.println(id_praca);
	
		
		
		/*
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
		*/
		

		
		
				for(int i = 0;i<2338;i++){
					
					
					dataHoraIni = ano+eam+mes+emd+dia+edh+hora+":00:00";
					
					
					//----------------hora------------------
					hora = hora+1;			
					if(hora == 24){
						hora = 0;
						dia = dia + 1;
					}			
					if(hora < 10){
						edh = " 0";
					}else{
						edh = " ";
					}
					
					
					//--------------------mes------------------------
					if((mes == 01)&&(dia == 32)){
						mes = 02;
						dia = 1;				
					}
					
					if((mes == 02)&&(dia == 29)){
						mes = 03;
						dia = 1;				
					}
					
					if((mes == 03)&&(dia == 32)){
						mes = 04;
						dia = 1;
						ano = 2015;
					}
					
					if((mes == 04)&&(dia == 31)){
						mes = 05;
						dia = 1;
					}
					
					if((mes == 05)&&(dia == 32)){
						mes = 06;
						dia = 1;
					}		
					
					if((mes == 06)&&(dia == 31)){
						mes = 07;
						dia = 1;
					}	
					
					if((mes == 07)&&(dia == 32)){
						mes = 8;
						dia = 1;
					}	
					
					
					if((mes == 8)&&(dia == 32)){
						mes = 9;
						dia = 1;
					}	
					
					if((mes == 9)&&(dia == 31)){
						mes = 10;
						dia = 1;
					}	
					
					if((mes == 10)&&(dia == 32)){
						mes = 11;
						dia = 1;
					}
					
					
					if(dia < 10){
						emd = "-0";
					}else{
						emd = "-";
					}
					
					if(mes < 10){
						eam = "-0";
					}else{
						eam= "-";
					}
					
					
					
					dataHoraFim = ano+eam+mes+emd+dia+edh+hora+":00:00";
					horaCerta =  ano+eam+mes+emd+dia+edh+hora+":00:00";		
					
			//		System.out.println(dataHoraIni+" | "+dataHoraFim+" | "+horaCerta);
					
				
					 DecimalFormat fmt = new DecimalFormat("0"); 
					 DecimalFormat eS = new DecimalFormat("0.00");
					
					a.setSql("SELECT "
							+ "Avg(dados_praca.entrada), "
							+ "Avg(dados_praca.saida), "
							+ "Avg(dados_praca.usuarios) "
							+ "FROM "
							+ "dados_praca "
							+ "WHERE "
							+ "dados_praca.id_praca =  '"+id_praca+"' AND "
							+ "dados_praca.id_data_hora BETWEEN  '"+dataHoraIni+"' AND '"+dataHoraFim+"'");
					
					ResultSet rs = a.consulta();
					try {
					
						while(rs.next()){
							
						//	System.out.println(rs.getString(1)+" | "+rs.getString(2)+" | "+rs.getString(3));
							String sql1a,sql2a;
							sql1a = "INSERT INTO `dados_praca_por_hora` (`id_praca`,`id_data_hora`";
							sql2a = ") VALUES ('"+id_praca+"','"+horaCerta+"'";
							
							
							if(!(rs.getString(1) == null)){
								double en = Double.parseDouble(rs.getString(1));
								numEn = eS.format(en);
								numEn = numEn.replace(",",".");
								sql1a = sql1a + ",`entrada`,`un_entrada`";
								sql2a = sql2a + ",'"+numEn+"','K'";
								
							}
							
						
							if(!(rs.getString(2) == null)){
								double sa = Double.parseDouble(rs.getString(2));
								numSa = eS.format(sa);
								numSa = numSa.replace(",",".");
								sql1a = sql1a + ",`saida`,`un_saida`";
								sql2a = sql2a + ",'"+numSa+"','K'";
							}
							
							if(!(rs.getString(3) == null)){
								double us = Double.parseDouble(rs.getString(3));
								numUs = fmt.format(us);
								sql1a = sql1a + ",`usuarios`";
								sql2a = sql2a + ",'"+numUs+"'";
							}
						
												
						//	System.out.println(rs.getString(1)+" | "+rs.getString(2)+" | "+rs.getString(3));
						//	System.out.println(id_praca+" | "+horaCerta+" | "+numEn+" | "+numSa+" | "+numUs);
							
							ac.setSql(sql1a+sql2a+")");
							System.out.println(ac.getSql());
							ac.executa();
							
							
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				
		
					
					
				}
		
		
		
		
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
			
			
		
		
		
		a.fecharConexao();
		
		
	}

}
