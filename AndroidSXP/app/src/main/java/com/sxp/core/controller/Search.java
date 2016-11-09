package com.sxp.core.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/*import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;*/
import org.glassfish.jersey.server.ChunkedOutput;

import com.fasterxml.jackson.core.type.TypeReference;

import com.sxp.core.controller.tools.JsonTools;
import com.sxp.core.model.api.Manager;
import com.sxp.core.model.api.ManagerListener;
import com.sxp.core.model.entity.Item;
import com.sxp.core.model.factory.ManagerFactory;
import com.sxp.core.network.api.ItemRequestService;
import com.sxp.core.network.api.Messages;
import com.sxp.core.network.api.SearchListener;
import com.sxp.core.network.api.ServiceListener;
import com.sxp.core.network.api.service.Service;
import com.sxp.core.network.impl.advertisement.ItemAdvertisement;
//import com.network.impl.jxta.JxtaItemService;
//import com.network.impl.jxta.JxtaItemsSenderService;
import com.sxp.core.network.impl.peerdroid.PeerDroidItemService;
import com.sxp.core.network.impl.peerdroid.PeerDroidItemsSenderService;
import com.sxp.core.rest.api.Authentifier;
import com.sxp.core.rest.api.ServletPath;

@ServletPath("/api/search/*")
//@Path("/")
public class Search {
    //@GET
    //@Path("/simple2")
    //public ChunkedOutput<String> chunckedSearchByTitle(@QueryParam("title") final String title, @HeaderParam(Authentifier.PARAM_NAME) final String token) {
    public ChunkedOutput<String> chunckedSearchByTitle(final String title, final String token) {
        final ChunkedOutput<String> output = new ChunkedOutput<String>(String.class);

        new Thread(new Runnable() {

            @Override
            public void run() {
                final ItemRequestService itemSender = (ItemRequestService) Application.getInstance().getPeer().getService(PeerDroidItemsSenderService.NAME);
                Service items = Application.getInstance().getPeer().getService(PeerDroidItemService.NAME);
                itemSender.addListener(new ServiceListener() {

                    @Override
                    public void notify(Messages messages) {
                        try {
                            output.write(messages.getMessage("items"));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            try {
                                output.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                }, token == null ? "test":token);

                items.search("title", title, new SearchListener<ItemAdvertisement>() {
                    @Override
                    public void notify(Collection<ItemAdvertisement> result) {
                        ArrayList<String> uids = new ArrayList<>();
                        for(ItemAdvertisement i: result) {
                            uids.add(i.getSourceURI());
                        }
                        itemSender.sendRequest(title, token == null ? "test":token, uids.toArray(new String[1]));
                    }

                });
                try {
                    Thread.sleep(3000);
                    itemSender.removeListener(token == null ? "test":token);
                    try {
                        output.write("[]");
                        output.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } catch (InterruptedException e) {

                }
            }

        }).start();

        return output;
    }

    //@GET
    //@Path("/simple")
    //public ChunkedOutput<String> chunckedSearchByTitle2(@QueryParam("title") final String title, @HeaderParam(Authentifier.PARAM_NAME) final String token) {
    public ChunkedOutput<String> chunckedSearchByTitle2(final String title, final String token) {
        final ChunkedOutput<String> output = new ChunkedOutput<String>(String.class);

        new Thread(new Runnable() {

            @Override
            public void run() {
                Manager<Item> em =
                        ManagerFactory.createNetworkResilianceItemManager(Application.getInstance().getPeer(), token);
                em.findAllByAttribute("title", title, new ManagerListener<Item>() {

                    @Override
                    public void notify(Collection<Item> results) {
                        JsonTools<Collection<Item>> json = new JsonTools<>(new TypeReference<Collection<Item>>(){});
                        try {
                            if(!results.isEmpty()) {
                                output.write(json.toJson(results));
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        output.write("[]");
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();

        return output;
    }
}
