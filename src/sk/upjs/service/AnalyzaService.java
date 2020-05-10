package sk.upjs.service;

public interface AnalyzaService {


	/**
	 * Vyrobi subor PLY s lokalnymi minimami celeho 3D modelu. Vyuziva algoritmus z
	 * clanku: Silvestre I., Rodrigues J.I., Figueiredo M. and Veiga-Pires C., 2014.
	 * High-resolution digital 3D models of Algar do Penico Chamber: limitations,
	 * challenges, and potential. International Journal of Speleology, 44 (1),
	 * 25-35. Tampa, FL (USA) ISSN 0392-6672
	 * http://dx.doi.org/10.5038/1827-806X.44.1.3
	 * 
	 * @param cielovyAdresar
	 *            do ktoreho sa maju ulozit novovytvorene subory
	 * @param nazovSuboru
	 *            nazov noveho suboru do ktoreho sa ulozia vrcholy
	 * @return true ak sa podarilo vyrobit subor
	 */
	public boolean vyrobSuborDijkstraAlg(String cielovyAdresar, String nazovSuboru);

	public boolean vyrobSuborNajkratsiaCestaGuass(String cielovyAdresar, String nazovSuboru);

	public boolean vyrobSuborNaismith(String cielovyAdresar, String nazovSuboru);

	public boolean vyrobSuborTobler(String cielovyAdresar, String nazovSuboru);

}
