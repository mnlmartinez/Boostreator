package es.boostreator.dao.imp;

import es.boostreator.dao.CoffeeMachineDao;
import es.boostreator.dao.CoffeeMachineSiteDao;
import es.boostreator.domain.model.enums.Site;

public class CoffeeMachineDaoImp implements CoffeeMachineDao {

    private CoffeeMachineSiteDao coffeeMachineFnacDAO = new CoffeeMachineFnacDaoImp();

    public CoffeeMachineSiteDao getCoffeeMachineDAO(Site site) {
        switch (site) {
            case FNAC:
                return this.coffeeMachineFnacDAO;
            default:
                return null;
        }
    }

}
