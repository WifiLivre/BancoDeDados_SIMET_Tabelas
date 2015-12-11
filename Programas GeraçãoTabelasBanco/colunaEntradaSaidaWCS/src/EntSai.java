import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;



public class EntSai {
	
	public void trocaColuna(){
		AcessaBanco a1 = new AcessaBanco();
		AcessaBanco a2 = new AcessaBanco();
		
		
		a1.setSql("SELECT "
				+ "dados_praca.id_data_hora, "
				+ "dados_praca.id_praca, "
				+ "dados_praca.entrada, "
				+ "dados_praca.saida "
				+ "FROM "
				+ "dados_praca "
				+ "WHERE "
				+ "dados_praca.id_praca >  '900'"
				+ " AND dados_praca.entradaWCS IS NULL");
		System.out.println(a1.getSql());
		a1.abrirConexao();
		a2.abrirConexao();
		ResultSet rs1 = a1.consulta();
		try {
			while(rs1.next()){
				
				a2.setSql("UPDATE `dados_praca` SET `entrada`=NULL,`saida`=NULL,`entradaWCS`='"+ rs1.getString(3)+"',`saidaWCS`='"+ rs1.getString(4)+"' WHERE (`id_data_hora`='"+ rs1.getString(1)+"') AND (`id_praca`='"+ rs1.getString(2)+"')");
				//UPDATE `dados_praca` SET `entrada`=NULL,`saida`=NULL,`entradaWCS`='2993895295',`saidaWCS`='2941491328' WHERE (`id_data_hora`='2014-10-28 18:00:01') AND (`id_praca`='1005')  
				a2.executa();					
				
				System.out.println(rs1.getString(2)+" | "+ rs1.getString(1)+" | "+rs1.getString(3)+" | "+rs1.getString(4));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		a2.fecharConexao();
		a1.fecharConexao();
		
		System.out.println("FEITO");
		
	}
	
	
	
	
	public void calculaWCSKbits(){
		AcessaBanco a3 = new AcessaBanco();
		AcessaBanco a4 = new AcessaBanco();
	
		AcessaBanco a20 = new AcessaBanco();
		a20.setSql("SELECT praca.id_praca FROM praca WHERE praca.id_praca > '900'");
		a20.abrirConexao();
		a3.abrirConexao();
		a4.abrirConexao();
		
		ResultSet rs20 = a20.consulta();
		try {
			while(rs20.next()){
				String id_praca = rs20.getString(1);
				double entradaAnt = 0;
				double saidaAnt = 0;
				
				
				a3.setSql("SELECT "
						+ "dados_praca.id_data_hora, "
						+ "dados_praca.id_praca, "
						+ "dados_praca.entradaWCS, "
						+ "dados_praca.saidaWCS "
						+ "FROM "
						+ "dados_praca "
						+ "WHERE "
						+ "dados_praca.id_praca =  '"+id_praca+"' AND "
						+ "dados_praca.un_entrada IS NULL AND "
						+ "dados_praca.entradaWCS IS NOT NULL ");
				
				System.out.println(a3.getSql());
			

				ResultSet rs2 = a3.consulta();
				try {
					while(rs2.next()){
			
						
						
						double entrada = Double.parseDouble(rs2.getString(3));
						if(entrada < entradaAnt){
							double aux = 4294967296.0 - entradaAnt;
							entrada = ((aux+entrada)/1024)/600;
						}else{
							entrada = ((entrada - entradaAnt)/1024)/600;
						}								
						entradaAnt = Double.parseDouble(rs2.getString(3));
						
						
						
						double saida = Double.parseDouble(rs2.getString(4));
						if(saida < saidaAnt){
							double aux1 = 4294967296.0 - saidaAnt;
							saida = ((aux1+saida)/1024)/600;
						}else{
							saida = ((saida - saidaAnt)/1024)/600;
						}							
						saidaAnt = Double.parseDouble(rs2.getString(4));
						
						System.out.println(rs2.getString(2)+" | "+ rs2.getString(1)+" | "+entrada+" | K | "+saida+" | K ");
						
						a4.setSql("UPDATE `dados_praca` SET `entrada`='"+entrada+"',`un_entrada`='K',`saida`='"+saida+"',`un_saida`='K' WHERE (`id_data_hora`='"+rs2.getString(1)+"') AND (`id_praca`='"+id_praca+"')");
						a4.executa();
						
							
						
						
						
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		a4.fecharConexao();
		a3.fecharConexao();
	
		a20.fecharConexao();
		
		System.out.println("FEITO");
	}
	
	public void removeDuplicata(){
		
		AcessaBanco a = new AcessaBanco();
		a.abrirConexao();
		
		AcessaBanco a1 = new AcessaBanco();
		a1.abrirConexao();
		
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
				+ "dados_praca.id_data_hora BETWEEN  '2015-04-30 23:30:00' AND '2015-08-01 00:30:00'");
		
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
		
		String dataHoraIni = "2015-04-30 23:30:00";
		@SuppressWarnings("unused")
		String dataHoraFim = "2015-08-01 00:30:00";

		@SuppressWarnings("unused")
		String horaCerta = null;
		
		String eam,emd,edh,minutoRange; //tipos de espaços m(mês dia, ano mes, dia hora)
		int ano, mes, dia, hora;

		
		ano = 2015;
		eam = "-";
		mes = 04;
		emd = "-";
		dia = 30;
		edh = " ";
		hora = 23;
		
		minutoRange = "50";
		
		
		
		
		String id_praca= "1001";	
		
		
		
		ab.setSql("SELECT DISTINCT "
				+ "dados_praca.id_praca "
				+ "FROM "
				+ "dados_praca "
				+ "WHERE "
				+ "dados_praca.id_data_hora <  '2015-05-01 00:00:09' AND "
				+ "dados_praca.id_praca BETWEEN  '1001' AND '1070'");
		
		ab.abrirConexao();
		ResultSet rs3 = ab.consulta();
		try {
			while(rs3.next()){
				
				
				ano = 2015;
				eam = "-";
				mes = 04;
				emd = "-";
				dia = 30;
				edh = " ";
				hora = 23;
				
				
				
				
				
				id_praca = rs3.getString(1);
				System.out.println(id_praca);
	
		
		


		
	
				for(int i = 0;i<2138;i++){
					
					
					dataHoraIni = ano+eam+mes+emd+dia+edh+hora+":"+minutoRange+":%";
					
					
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
						ano = 2015;
					}
					
					if((mes == 07)&&(dia == 32)){
						mes = 8;
						dia = 1;
					}
					
					if((mes == 8)&&(dia == 32)){
						mes = 9;
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
					
					
					
				

					
					a.setSql("SELECT "
							+ "dados_praca.id_data_hora "
							+ "FROM "
							+ "dados_praca "
							+ "WHERE "
							+ "dados_praca.id_praca =  '"+id_praca+"' AND "
							+ "dados_praca.id_data_hora LIKE '"+dataHoraIni+"'");
					
					ResultSet rs = a.consulta();
					try {
					
					
						rs.next();
						
						while(rs.next()){
							
							a1.setSql("DELETE FROM `dados_praca` WHERE (`id_data_hora`='"+rs.getString(1)+"') AND (`id_praca`='"+id_praca+"')  ");
							a1.executa();
							
						}
						a1.setSql(null);
						rs = null;
					} catch (SQLException e) {
						e.printStackTrace();
					}
					a.setSql(null);
				}
			
		
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
			
			
		
		
		
		a.fecharConexao();
		
		a1.fecharConexao();
		System.out.println("FEITO");
		
		
	}
	
	public void diminuiLarguraBanda(){
		
		AcessaBanco a1 = new AcessaBanco();
		AcessaBanco a2 = new AcessaBanco();
		AcessaBanco a3 = new AcessaBanco();
		
		a1.setSql("SELECT "
				+ "praca.id_praca, "
				+ "praca.n_max_usuarios "
				+ "FROM "
				+ "praca ");
		System.out.println(a1.getSql());
		a1.abrirConexao();
		a2.abrirConexao();
		a3.abrirConexao();
		ResultSet rs1 = a1.consulta();
		try {
			while(rs1.next()){
				double max_banda = 0;
				max_banda = Double.parseDouble(rs1.getString(2))*512;
				max_banda = converterDoubleDoisDecimais(max_banda);
				
			//	System.out.println(rs1.getString(1)+" | "+ rs1.getString(2)+" | "+max_banda);
				a2.setSql("SELECT "
						+ "dados_praca.id_praca, "
						+ "dados_praca.entrada, "
						+ "dados_praca.id_data_hora "
						+ "FROM "
						+ "dados_praca "
						+ "WHERE "
						+ "dados_praca.id_praca =  '"+rs1.getString(1)+"' AND "
						+ "dados_praca.entrada >  '"+max_banda+"'");
				ResultSet rs2 = a2.consulta();
				try {
					while(rs2.next()){
						double dim_banda = 0;
						dim_banda = Double.parseDouble(rs2.getString(2))/10;
						dim_banda = converterDoubleDoisDecimais(dim_banda);
						
						
						System.out.println(rs1.getString(1)+" | "+ rs1.getString(2)+" | "+max_banda+" | "+rs2.getString(2)+" | "+dim_banda);
						a3.setSql("UPDATE `dados_praca` SET `entrada`='"+dim_banda+"' WHERE (`id_data_hora`='"+rs2.getString(3)+"') AND (`id_praca`='"+ rs1.getString(1)+ "')");
									
							
						
						System.out.println(a3.getSql());
						a3.executa();		
						
						
						
						
						
						
						
					}
				} catch (SQLException e) {
		
					e.printStackTrace();
				}
				
			
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		a3.fecharConexao();
		a2.fecharConexao();
		a1.fecharConexao();
		
		

		
		
		System.out.println("FIM");
		
	
	}
	
	public static double converterDoubleDoisDecimais(double precoDouble) {  
	    DecimalFormat fmt = new DecimalFormat("0.0");        
	    String string = fmt.format(precoDouble);  
	    String[] part = string.split("[,]");  
	    String string2 = part[0];  
	        double preco = Double.parseDouble(string2);  
	    return preco;  
	} 
	
	public void transforma10minParaHora(String ultimaPracaId){
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
				+ "dados_praca.id_data_hora BETWEEN  '2015-04-30 23:30:00' AND '2015-05-01 00:30:00'");
		
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
		
		String dataHoraIni = "2015-07-30 00:00:00";
		String dataHoraFim = "2015-11-01 23:50:00";

		
	//	String dataHoraFim = "2015-08-31 00:30:00";

		
		String horaCerta = null;
		
		String eam,emd,edh; //tipos de espaços m(mês dia, ano mes, dia hora)
		int ano, mes, dia, hora;
		
		String numUs = null;
		String numEn = null;
		String numSa = null;
		
		ano = 2014;
		eam = "-";
		mes = 8;
		emd = "-";
		dia = 30;
		edh = " ";
		hora = 00;
		
		
		String id_praca= "1001";	
		
		
	/*	
		ab.setSql("SELECT DISTINCT "
				+ "dados_praca.id_praca "
				+ "FROM "
				+ "dados_praca "
				+ "WHERE "
				+ "dados_praca.id_data_hora <  '2015-05-01 00:00:09' AND "
				+ "dados_praca.id_praca BETWEEN  '"+ultimaPracaId+"' AND '1070'");
		*/
		ab.setSql("SELECT DISTINCT "
				+ "dados_praca.id_praca "
				+ "FROM "
				+ "dados_praca "
				+ "WHERE "
				+ "dados_praca.id_praca BETWEEN  '"+ultimaPracaId+"' AND '1070'");
		
		ab.abrirConexao();
		ResultSet rs3 = ab.consulta();
		try {
			while(rs3.next()){
				
				
				ano = 2015;
				eam = "-";
				mes = 7;
				emd = "-";
				dia = 30;
				edh = " ";
				hora = 00;
				
				
				
				
				
				id_praca = rs3.getString(1);
				System.out.println(id_praca);
	
		
		
		/*
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
		*/
		

		
		
				for(int i = 0;i<3496;i++){
					
					
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
					if((mes == 12)&&(dia == 32)&&(ano == 2014)){
						mes = 01;
						dia = 01;
						ano = 2015;
					}
					
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
						ano = 2015;
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
					
					if((mes == 11)&&(dia == 31)){
						mes = 12;
						dia = 1;
					}
					
					if((mes == 12)&&(dia == 32)&&(ano == 2015)){
						mes = 01;
						dia = 01;
						ano = 2016;
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
