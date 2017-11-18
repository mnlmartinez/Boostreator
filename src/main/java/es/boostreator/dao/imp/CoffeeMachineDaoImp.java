package es.boostreator.dao.imp;

import es.boostreator.dao.CoffeeMachineDao;
import es.boostreator.dao.CoffeeMachineSiteDao;
import es.boostreator.domain.model.enums.Site;

public class CoffeeMachineDaoImp implements CoffeeMachineDao {

    private CoffeeMachineSiteDao coffeeMachineFnacDao = new CoffeeMachineFnacDaoImp();
    private CoffeeMachineSiteDao coffeeMachineECIDao = new CoffeeMachineECIDaoImp();

    public CoffeeMachineSiteDao getCoffeeMachineDao(Site site) {
        switch (site) {
            case FNAC:
                return this.coffeeMachineFnacDao;
            case ELCORTEINGLES:
                return this.coffeeMachineECIDao;
            default:
                return null;
        }
    }

}
