package sk.upjs.service;

import java.io.File;
import java.util.List;

import sk.upjs.entity.NacitanySubor;
import sk.upjs.entity.Vertex;
import sk.upjs.pracaSDatami.Algoritmy;
import sk.upjs.pracaSDatami.AlgoritmyImplementacia;
import sk.upjs.pracaSDatami.VyrobcaANacitavacSuboruPLY;

/**
 * Pre spravne fungovanie treba spustit inicializaciu
 *
 */
public class AnalyzaServiceImplementacia implements AnalyzaService {

	private final File suborPLY;
	private NacitanySubor nacitanySubor;
	private Algoritmy algoritmy;

	public AnalyzaServiceImplementacia(File suborPLY) {
		this.suborPLY = suborPLY;
	}

	/**
	 * @return ci sa podarila inicializacia
	 */
	public boolean inicializacia() {
		this.nacitanySubor = VyrobcaANacitavacSuboruPLY.nacitajSubor(suborPLY);
		this.algoritmy = new AlgoritmyImplementacia(nacitanySubor);
		return !(nacitanySubor == null);
	}

	public boolean isNacitanieSuboruOK() {
		return !(nacitanySubor == null);
	}


	public boolean vyrobSuborDijkstraAlg(String cielovyAdresar, String nazovSuboru) {
		if (nacitanySubor == null || cielovyAdresar == null) {
			return false;
		}


		List<Vertex> minima = algoritmy.najkratsiaCestaDijk();
		System.out.println("Vykonane");
		return VyrobcaANacitavacSuboruPLY.vyrobSuborPLY(nacitanySubor.getHlavickaSuboru(), cielovyAdresar, nazovSuboru,
				minima, false, "Dijkstra alg " );

	}
	public boolean vyrobSuborNaismith(String cielovyAdresar, String nazovSuboru) {
		if (nacitanySubor == null || cielovyAdresar == null) {
			return false;
		}


		List<Vertex> minima = algoritmy.najkratsiaCestaNaismith();
		System.out.println("Vykonane");
		return VyrobcaANacitavacSuboruPLY.vyrobSuborPLY(nacitanySubor.getHlavickaSuboru(), cielovyAdresar, nazovSuboru,
				minima, false, "NajkratsiaCestaNaismith " );

	}
	public boolean vyrobSuborNajkratsiaCestaGuass(String cielovyAdresar, String nazovSuboru) {
		if (nacitanySubor == null || cielovyAdresar == null) {
			return false;
		}


		List<Vertex> minima = algoritmy.najkratsiaCestaIrmischer();
		System.out.println("Vykonane");
		return VyrobcaANacitavacSuboruPLY.vyrobSuborPLY(nacitanySubor.getHlavickaSuboru(), cielovyAdresar, nazovSuboru,
				minima, false, "NajkratsiaCestaIrmischer" );

	}
	public boolean vyrobSuborTobler(String cielovyAdresar, String nazovSuboru) {
		if (nacitanySubor == null || cielovyAdresar == null) {
			return false;
		}


		List<Vertex> minima = algoritmy.najkratsiaCestaTobler();
		System.out.println("Vykonane");
		return VyrobcaANacitavacSuboruPLY.vyrobSuborPLY(nacitanySubor.getHlavickaSuboru(), cielovyAdresar, nazovSuboru,
				minima, false, "NajkratsiaCestaTobler" );

	}

}
