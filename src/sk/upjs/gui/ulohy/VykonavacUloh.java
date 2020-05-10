package sk.upjs.gui.ulohy;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import sk.upjs.gui.VykonavaSaUlohaAStav;
import sk.upjs.service.AnalyzaServiceImplementacia;

/**
 * Rozne konstruktory vyrobia vzdy ulohu pre vykonanie jedneho algoritmu a ked
 * sa tato trieda spusti v osobitnom vlakne tak sa vyrobena uloha spusti v
 * osobitnom vlakne, ktore ked dobehne a vrati vysledok tak ten je zapisany do
 * zdielanej triedy VykonavaSaUlohaAStav aby okno aplikacie vedelo zistit, ze
 * uloha dobehla a ako skoncila
 */
public class VykonavacUloh implements Runnable {

	private VypocetUloha uloha;
	private VykonavaSaUlohaAStav vykonavaSaUlohaAStav;

	// --------------------------------------------------------
	// konstruktory, kde kazdy konstruktor zodpoveda obsluhe jedmeho algoritmu

	// Inicializacia
	public VykonavacUloh(VykonavaSaUlohaAStav vykonavaSaUloha, AnalyzaServiceImplementacia service) {
		uloha = new InicializaciaUloha(service);
		this.vykonavaSaUlohaAStav = vykonavaSaUloha;
	}


	public VykonavacUloh(int cisloAlgoritmu, VykonavaSaUlohaAStav vykonavaSaUlohaAStav,
			AnalyzaServiceImplementacia service, String cielovyAdresar, String nazovSuboru) {
		this.vykonavaSaUlohaAStav = vykonavaSaUlohaAStav;

		if( cisloAlgoritmu == 0) {
			this.uloha = new DijkstraAlgUloha(service, cielovyAdresar, nazovSuboru);
		}

		if(cisloAlgoritmu == 1){
			this.uloha = new NajkratsiaCestaGauss(service, cielovyAdresar, nazovSuboru);
		}

		if(cisloAlgoritmu == 2){
			this.uloha = new NajkratsiaCestaNaismith(service, cielovyAdresar, nazovSuboru);
		}
		if(cisloAlgoritmu == 3){
			this.uloha = new NajkratsiaCestaTobler(service, cielovyAdresar, nazovSuboru);
		}

	}



	@Override
	public void run() {
		ExecutorService exekutor = Executors.newSingleThreadExecutor();
		Future<Boolean> f = exekutor.submit(uloha);

		vykonavaSaUlohaAStav.setVysledokVykonania(false);
		try {
			vykonavaSaUlohaAStav.setVysledokVykonania(f.get());
			vykonavaSaUlohaAStav.setPrebiehaVykonavanie(false);
		} catch (InterruptedException | ExecutionException e1) {
			// ked sa prerusi nacitanie, napr. tlacitkom
			vykonavaSaUlohaAStav.setVysledokVykonania(false);
			vykonavaSaUlohaAStav.setPrebiehaVykonavanie(false);
		}
	}

}
