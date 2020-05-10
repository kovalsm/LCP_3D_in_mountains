package sk.upjs.pracaSDatami;

import java.util.*;

import sk.upjs.entity.NacitanySubor;
import sk.upjs.entity.Vertex;

public class AlgoritmyImplementacia implements Algoritmy {

	private final NacitanySubor nacitanySubor;

	public AlgoritmyImplementacia(NacitanySubor nacitanySubor) {
		this.nacitanySubor = nacitanySubor;
	}


	public List<Vertex> najkratsiaCestaIrmischer(){
		if (nacitanySubor == null) {
			System.out.println("A");
			return null;
		}

		List<Vertex> cesta = new ArrayList<Vertex>();
		Vertex bodZac = null;
		Vertex bodCiel = null;


		int min = Integer.MAX_VALUE, min_index = -1;
		int pocetVrcholov = nacitanySubor.getPocetVrcholov();

		//nájdenie vrcholu A a B. Body vybraté z CloudCompare cez ich IDx
		for (int i=0; i< pocetVrcholov-1; i++){
			if(nacitanySubor.getVrchol(i).getIdxZPLY()== 549880){
				System.out.println("bodA");
				bodZac =  nacitanySubor.getVrchol(i);
			}
			if(nacitanySubor.getVrchol(i).getIdxZPLY()== 950361){
				System.out.println("bodB");
				bodCiel =  nacitanySubor.getVrchol(i);
			}
		}

		//pole vzdialenostMedziBodmi bude udrziavat najkratsiu vzdialenost z bodu A do bodu B podla najkratsieho casu casMedziBodmi
		double vzdialenostMedziBodmi [] = new double[pocetVrcholov];
		double casMedziBodmi [] = new double[pocetVrcholov];
		//sptSet bude zaznacovat ktore vrcholy patria do najkratsej cesty
		boolean sptSet[] = new boolean[pocetVrcholov];
		double vzdusnaVzd = vzdialenostMedziVrcholmi(bodZac, bodCiel);

		//inicializacia vsetkych vzdialenosti a casu na INF a sptSet na false
		for (int i=0; i< pocetVrcholov; i++){
			vzdialenostMedziBodmi[i] = Integer.MAX_VALUE;
			casMedziBodmi[i] = Integer.MAX_VALUE;
			sptSet[i] = false;
		}

		vzdialenostMedziBodmi[bodZac.getIdxZPLY()] = 0;
		casMedziBodmi[bodZac.getIdxZPLY()] = 0;

		for (int i=0; i< pocetVrcholov-1; i++){
			int u = minDistance(casMedziBodmi, sptSet);
			sptSet[u] = true;
			Vertex vrcholAkt = nacitanySubor.getVrchol(u);
			List<Integer> susedia = vrcholAkt.getSusedia();
			for (int v=0; v< vrcholAkt.getSusedia().size(); v++){
				Vertex vrcholVybraty = nacitanySubor.getVrchol(susedia.get(v));
				double vzdialenostMedziVrcholmi = vzdialenostMedziVrcholmi(vrcholVybraty, vrcholAkt);
				double casIrmischerMedziVrcholmi = vzdialenostMedziVrcholmi / vypocetRychlostiImrischer(vrcholAkt, vrcholVybraty);
				int idx = vrcholVybraty.getIdxZPLY();
				if (!sptSet[idx]  && casMedziBodmi[u] != Integer.MAX_VALUE &&
						casIrmischerMedziVrcholmi + casMedziBodmi[u] < casMedziBodmi[idx]) {
					casMedziBodmi[idx] = casMedziBodmi[u] + casIrmischerMedziVrcholmi;
					vzdialenostMedziBodmi[idx] = vzdialenostMedziBodmi[u] + vzdialenostMedziVrcholmi;
					if(idx!=bodZac.getIdxZPLY()){
						vrcholVybraty.setPredchadzajuci(vrcholAkt);
					}
				}
			}
		}


		System.out.println("Irmischer fcia");
		System.out.println("Dlzka cesty"+ vzdialenostMedziBodmi[bodCiel.getIdxZPLY()]);
		System.out.println("Cas cesty"+ casMedziBodmi[bodCiel.getIdxZPLY()]);

		//najde najkratsiu cestu
		for(Vertex vertex = bodCiel; vertex!=null; vertex = vertex.getPredchadzajuci()){
			cesta.add(zmenFarbu(vertex, 189, 211, 38));
		}
		Collections.reverse(cesta);

		return  cesta;
	}
	public List<Vertex> najkratsiaCestaTobler(){
		if (nacitanySubor == null) {
			System.out.println("Nejde nacitat");
			return null;
		}

		List<Vertex> cesta = new ArrayList<Vertex>();
		Vertex bodZac = null;
		Vertex bodCiel = null;


		int min = Integer.MAX_VALUE, min_index = -1;
		int pocetVrcholov = nacitanySubor.getPocetVrcholov();

		//nájdenie vrcholu A a B. Body vybraté z CloudCompare cez ich IDx
		for (int i=0; i< pocetVrcholov-1; i++){
			if(nacitanySubor.getVrchol(i).getIdxZPLY()== 271084){
				System.out.println("bodA");
				bodZac =  nacitanySubor.getVrchol(i);
			}
			if(nacitanySubor.getVrchol(i).getIdxZPLY()== 614839){
				System.out.println("bodB");
				bodCiel =  nacitanySubor.getVrchol(i);
			}
		}

		//pole dijk bude udrziavat najkratsiu vzdialenost z bodu A do bodu B
		double dist [] = new double[pocetVrcholov];
		double time [] = new double[pocetVrcholov];
		//sptSet bude zaznacovat ktore vrcholy patria do najkratsej cesty
		boolean sptSet[] = new boolean[pocetVrcholov];
		double vzdusnaVzd = vzdialenostMedziVrcholmi(bodZac, bodCiel);
		//inicializacia vsetkych vzdialenosti na INF a sptSet na false
		for (int i=0; i< pocetVrcholov; i++){
			dist[i] = Integer.MAX_VALUE;
			time[i] = Integer.MAX_VALUE;
			sptSet[i] = false;
		}

		dist[bodZac.getIdxZPLY()] = 0;
		time[bodZac.getIdxZPLY()] = 0;
		System.out.println("Vykonáva sa Irmischer");

		for (int i=0; i< pocetVrcholov-1; i++){
		//	if(vzdialenostMedziVrcholmi(bodZac, nacitanySubor.getVrchol(i))<=vzdusnaVzd || vzdialenostMedziVrcholmi(bodCiel, nacitanySubor.getVrchol(i))<= vzdusnaVzd) {
				int u = minDistance(time, sptSet);
				sptSet[u] = true;
				Vertex vrcholAkt = nacitanySubor.getVrchol(u);
				List<Integer> susedia = vrcholAkt.getSusedia();
				for (int v=0; v< vrcholAkt.getSusedia().size(); v++){
					Vertex vrcholVybraty = nacitanySubor.getVrchol(susedia.get(v));
					double vzdialenostMedziVrcholmi = vzdialenostMedziVrcholmi(vrcholVybraty, vrcholAkt);
					int idx = vrcholVybraty.getIdxZPLY();
					if (!sptSet[idx]  && time[u] != Integer.MAX_VALUE  && (vzdialenostMedziVrcholmi / vypocetRychlostiTobler(vrcholAkt, vrcholVybraty)) + time[u] < time[idx]) {
						time[idx] = time[u] + (vzdialenostMedziVrcholmi / vypocetRychlostiTobler(vrcholAkt, vrcholVybraty));
						dist[idx] = dist[u] + vzdialenostMedziVrcholmi;
						if(idx!=bodZac.getIdxZPLY()){
							vrcholVybraty.setPredchadzajuci(vrcholAkt);
						//	System.out.println("Vrchol:"+ vrcholVybraty.getIdxZPLY());
						}
					}
			//	}
			}
		}
		System.out.println("Toblerova fcia");
		System.out.println("Dlzka cesty"+ dist[bodCiel.getIdxZPLY()]);
		System.out.println("Cas cesty"+ time[bodCiel.getIdxZPLY()]);
		for(Vertex vertex = bodCiel; vertex!=null; vertex = vertex.getPredchadzajuci()){
			cesta.add(zmenFarbu(vertex, 255, 32, 255)); //zmena z 0 na 255 r
		}
		Collections.reverse(cesta);

		return  cesta;
	}
	public List<Vertex> najkratsiaCestaDijk() {
		if (nacitanySubor == null) {
			System.out.println("A");
			return null;
		}

		Vertex bodZac = null;
		Vertex bodCiel = null;

		System.out.println("Vykonáva sa Dijkstra");
		int min = Integer.MAX_VALUE, min_index = -1;
		int pocetVrcholov = nacitanySubor.getPocetVrcholov();

		//nájdenie vrcholu A a B. Body vybraté z CloudCompare cez ich IDx
		for (int i=0; i< pocetVrcholov-1; i++){
			if(nacitanySubor.getVrchol(i).getIdxZPLY()== 549880){
				System.out.println("bodA");
				bodZac =  nacitanySubor.getVrchol(i);
			}
			if(nacitanySubor.getVrchol(i).getIdxZPLY()== 950361){
				System.out.println("bodB");
				bodCiel =  nacitanySubor.getVrchol(i);
			}
		}

		//pole dijk bude udrziavat najkratsiu vzdialenost z bodu A do bodu B
		double dist [] = new double[pocetVrcholov];

		//sptSet bude zaznacovat ktore vrcholy patria do najkratsej cesty
		boolean sptSet[] = new boolean[pocetVrcholov];
		double vzdusnaVzd = vzdialenostMedziVrcholmi(bodZac, bodCiel);
		//inicializacia vsetkych vzdialenosti na INF a sptSet na false
		for (int i=0; i< pocetVrcholov; i++){
			dist[i] = Integer.MAX_VALUE;

			sptSet[i] = false;
		}

		dist[bodZac.getIdxZPLY()] = 0;


		for (int i=0; i< pocetVrcholov-1; i++){
			//if(vzdialenostMedziVrcholmi(bodZac, nacitanySubor.getVrchol(i))<=vzdusnaVzd || vzdialenostMedziVrcholmi(bodCiel, nacitanySubor.getVrchol(i))<= vzdusnaVzd) {
				int u = minDistance(dist, sptSet);
				sptSet[u] = true;
				Vertex vrcholAkt = nacitanySubor.getVrchol(u);
				List<Integer> susedia = vrcholAkt.getSusedia();
				for (int v=0; v< vrcholAkt.getSusedia().size(); v++){
					Vertex vrcholVybraty = nacitanySubor.getVrchol(susedia.get(v));
					double vzdialenostMedziVrcholmi = vzdialenostMedziVrcholmi(vrcholVybraty, vrcholAkt);
					int idx = vrcholVybraty.getIdxZPLY();
					if (!sptSet[idx]  && dist[u] != Integer.MAX_VALUE && vzdialenostMedziVrcholmi + dist[u] < dist[idx]) {
						dist[idx] = dist[u] + vzdialenostMedziVrcholmi;
						if(idx!=bodZac.getIdxZPLY()){
							vrcholVybraty.setPredchadzajuci(vrcholAkt);
						}
					}
				//}
			}
		}
		System.out.println("Dijkstra fcia");
		System.out.println("Dlzka cesty"+ dist[bodCiel.getIdxZPLY()]);

		List<Vertex> cesta = new ArrayList<Vertex>();

		for(Vertex vertex = bodCiel; vertex!=null; vertex = vertex.getPredchadzajuci()){
			cesta.add(zmenFarbu(vertex, 245, 162, 15));
		}
		Collections.reverse(cesta);

		return cesta;
	}
	public List<Vertex> najkratsiaCestaNaismith() {
		if (nacitanySubor == null) {
			System.out.println("A");
			return null;
		}

		Vertex bodZac = null;
		Vertex bodCiel = null;

		System.out.println("Vykonáva sa Naismith");
		int pocetVrcholov = nacitanySubor.getPocetVrcholov();

		//nájdenie vrcholu A a B. Body vybraté z CloudCompare cez ich IDx
		for (int i=0; i< pocetVrcholov-1; i++){
			if(nacitanySubor.getVrchol(i).getIdxZPLY()== 549880){
				System.out.println("bodA");
				bodZac =  nacitanySubor.getVrchol(i);
			}
			if(nacitanySubor.getVrchol(i).getIdxZPLY()== 950361){
				System.out.println("bodB");
				bodCiel =  nacitanySubor.getVrchol(i);
			}
		}

		//pole dijk bude udrziavat najkratsiu vzdialenost z bodu A do bodu B
		double dist [] = new double[pocetVrcholov];
		double time[] = new double[pocetVrcholov];
		//sptSet bude zaznacovat ktore vrcholy patria do najkratsej cesty
		boolean sptSet[] = new boolean[pocetVrcholov];
		double vzdusnaVzd = vzdialenostMedziVrcholmi(bodZac, bodCiel);
		//inicializacia vsetkych vzdialenosti na INF a sptSet na false
		for (int i=0; i< pocetVrcholov; i++){
			dist[i] = Integer.MAX_VALUE;
			time[i] = Integer.MAX_VALUE;
			sptSet[i] = false;
		}

		dist[bodZac.getIdxZPLY()] = 0;
		time[bodZac.getIdxZPLY()] = 0;

		for (int i=0; i< pocetVrcholov-1; i++){
				int u = minDistance(time, sptSet);
				sptSet[u] = true;
				Vertex vrcholAkt = nacitanySubor.getVrchol(u);
				List<Integer> susedia = vrcholAkt.getSusedia();
				for (int v=0; v< vrcholAkt.getSusedia().size(); v++){
					Vertex vrcholVybraty = nacitanySubor.getVrchol(susedia.get(v));
					double vzdialenostMedziVrcholmi = vzdialenostMedziVrcholmi(vrcholVybraty, vrcholAkt);
					int idx = vrcholVybraty.getIdxZPLY();
					if (!sptSet[idx]  && time[u] != Integer.MAX_VALUE && casNaismith(vrcholAkt, vrcholVybraty) + time[u] < time[idx]) {
						dist[idx] = dist[u] + vzdialenostMedziVrcholmi;
						time[idx] = time[u] +  casNaismith(vrcholAkt, vrcholVybraty);
						if(idx!=bodZac.getIdxZPLY()){
							vrcholVybraty.setPredchadzajuci(vrcholAkt);
						}
					}
				}
		}
		System.out.println("Naismith fcia");
		System.out.println("Dlzka cesty"+ dist[bodCiel.getIdxZPLY()]);
		System.out.println("Cas cesty"+ time[bodCiel.getIdxZPLY()]);

		List<Vertex> cesta = new ArrayList<Vertex>();
		for(Vertex vertex = bodCiel; vertex!=null; vertex = vertex.getPredchadzajuci()){
			cesta.add(zmenFarbu(vertex, 255, 51, 86));
		}
		Collections.reverse(cesta);

		return cesta;
	}
	int minDistance(double dist[], boolean sptSet[])
	{
		// Initialize min value
		double min = Double.MAX_VALUE;
		int min_index = -1;

		for (int v = 0; v < nacitanySubor.getPocetVrcholov(); v++)
			if (sptSet[v] == false && dist[v] <= min) {
				min = dist[v];
				min_index = v;
			}

		return min_index;
	}

	double vzdialenostMedziVrcholmi(Vertex vrcholA, Vertex vrcholB){

		return Math.sqrt(Math.pow(vrcholB.getSuradnicaX()-vrcholA.getSuradnicaX(),2)+Math.pow(vrcholB.getSuradnicaY()-vrcholA.getSuradnicaY(),2)+Math.pow(vrcholB.getSuradnicaZ()- vrcholA.getSuradnicaZ(),2));

	}

	double vypocetRychlostiImrischer(Vertex vrcholA, Vertex vrcholB){
		double prevysenie = Math.abs(vrcholA.getSuradnicaZ()-vrcholB.getSuradnicaZ());
		double vzdialenost = vzdialenostMedziVrcholmi(vrcholA, vrcholB);
		double svah = (prevysenie/vzdialenost)*100;
		return  0.11 + 0.67 * Math.pow(Math.E, ((-Math.pow(svah + 2,2))/1800) );
	}

	double casNaismith(Vertex vrcholA, Vertex vrcholB){
		double correction = 0;
		double delta_S = Math.sqrt(Math.pow((vrcholA.getSuradnicaX()-vrcholB.getSuradnicaX()),2) + Math.pow((vrcholA.getSuradnicaY()-vrcholB.getSuradnicaY()),2));
		//double delta_S = vzdialenostMedziVrcholmi(vrcholA, vrcholB);
		double delta_H = Math.abs(vrcholA.getSuradnicaZ()-vrcholB.getSuradnicaZ());
		double distance = vzdialenostMedziVrcholmi(vrcholA, vrcholB);
		double slope = (delta_H/distance);

		if(slope >0){correction= 6;	}
		if(slope > -0.21 && slope < -0.09){correction= 1.9998;}
		if(slope < -0.21){correction= -1.9998;}

		double vypocet = 0.9*delta_S + correction * delta_H + slope*delta_S;

		return vypocet;
	}

	double vypocetRychlostiTobler(Vertex vrcholA, Vertex vrcholB){
		double elevation = Math.abs(vrcholA.getSuradnicaZ()-vrcholB.getSuradnicaZ());
		double distance = vzdialenostMedziVrcholmi(vrcholA, vrcholB);
		double slope = (elevation/distance);
		double vypocet =  1.06* Math.pow(Math.E, -3.5*Math.abs(slope + 0.05));

		return  vypocet;
	}

	Vertex zmenFarbu(Vertex vertex, int r, int g, int b){
		vertex.setFarbaB(b);
		vertex.setFarbaG(g);
		vertex.setFarbaR(r);

		String riadok = vertex.getSuradnicaX() + " " + vertex.getSuradnicaY() + " " + vertex.getSuradnicaZ() + " "
				+ vertex.getFarbaR() + " " + vertex.getFarbaG() + " " + vertex.getFarbaB() + " "
				+ vertex.getSuradnicaXNorm() + " " + vertex.getSuradnicaYNorm() + " " + vertex.getSuradnicaZNorm() + "\n";
		Vertex novyFarebnyVertex = new Vertex(vertex.getIdxZPLY(), riadok, vertex.getSuradnicaX(),  vertex.getSuradnicaY(), vertex.getSuradnicaZ(),
				vertex.getFarbaR(), vertex.getFarbaG() , vertex.getFarbaB(),
				vertex.getSuradnicaXNorm(),  vertex.getSuradnicaYNorm() , vertex.getSuradnicaZNorm());

		return novyFarebnyVertex;
	}
}
