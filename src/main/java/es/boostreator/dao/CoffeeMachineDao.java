package es.boostreator.dao;

import es.boostreator.domain.model.enums.Site;

public interface CoffeeMachineDao {

    CoffeeMachineSiteDao getCoffeeMachineDAO(Site site);

}
