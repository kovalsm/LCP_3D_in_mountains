package sk.upjs.gui.ulohy;
import sk.upjs.service.AnalyzaServiceImplementacia;


public class DijkstraAlgUloha extends VypocetUloha {



    private AnalyzaServiceImplementacia service;
    private String cielovyAdresar;
    private String nazovSuboru;

    public DijkstraAlgUloha(AnalyzaServiceImplementacia service, String cielovyAdresar,
                                           String nazovSuboru) {

        this.service = service;
        this.cielovyAdresar = cielovyAdresar;
        this.nazovSuboru = nazovSuboru;
    }

    @Override
    public Boolean call() throws Exception {
        boolean vysledokOk = service.vyrobSuborDijkstraAlg(cielovyAdresar, nazovSuboru);
        return vysledokOk;
    }

}