package org.nearbyshops.gidb;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Created by sumeet on 9/9/16.
 */

public class DependencyBinder extends AbstractBinder {

    @Override
    protected void configure() {

        // supply dependencies to ShopStaffResource
//        bind(ShopStaffResource.class).to(ShopStaffDAOPrepared.class).in(Singleton.class);
    }

}
