package com.sxp.core.controller;

import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.core.type.TypeReference;

import com.sxp.core.controller.tools.JsonTools;
import com.sxp.core.crypt.api.hashs.Hasher;
import com.sxp.core.crypt.factories.ElGamalAsymKeyFactory;
import com.sxp.core.crypt.factories.HasherFactory;
import com.sxp.core.model.api.SyncManager;
import com.sxp.core.model.api.UserSyncManager;
import com.sxp.core.model.entity.LoginToken;
import com.sxp.core.model.entity.User;
import com.sxp.core.model.syncManager.UserSyncManagerImpl;
import com.sxp.core.rest.api.Authentifier;
import com.sxp.core.rest.api.ServletPath;



@ServletPath("/api/users/*")
//@Path("/")
public class Users {
    /*@GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)*/
    //public String login(@QueryParam("login") String login, @QueryParam("password") String password) {
    public String login(String login, String password) {

        Authentifier auth = Application.getInstance().getAuth();
        UserSyncManager em = new UserSyncManagerImpl();
        User u = em.getUser(login, password);
        if(u != null) {
            LoginToken token = new LoginToken();
            token.setToken(auth.getToken(login, password));
            token.setUserid(u.getId());
            JsonTools<LoginToken> json = new JsonTools<>(new TypeReference<LoginToken>(){});
            return json.toJson(token);
        }
        return "{\"error\": \"true\"}";
		/*EntityManager<User> em = new UserManager();
		User u = em.findOneByAttribute("nick", login);
		if(u == null) return "{\"error\": \"true\"}";
		System.out.println("user trouve !");
		Hasher hasher = HasherFactory.createDefaultHasher();
		hasher.setSalt(u.getSalt());
		//check if passwords are the sames
		String hash1 = new String(u.getPasswordHash());
		String hash2 = new String(hasher.getHash(password.getBytes()));

		if(hash1.equals(hash2)) {
			LoginToken token = new LoginToken();
			token.setToken(auth.getToken(login, password));
			token.setUserid(u.getId());
			JsonTools<LoginToken> json = new JsonTools<>();
			json.initialize(LoginToken.class);
			return json.toJson(token);
		}

		return "{\"error\": \"true\"}";*/
    }

    //@GET
    // @Path("/logout")
    //public String logout(@HeaderParam(Authentifier.PARAM_NAME) String token) {
    public String logout(String token) {
        Authentifier auth = Application.getInstance().getAuth();
        auth.deleteToken(token);
        return null;
    }

    //@GET
    //@Path("/subscribe")
    //@Produces(MediaType.APPLICATION_JSON)
    //public String subscribe(@QueryParam("login") String login, @QueryParam("password") String password) {
    public String subscribe(String login, String password) {

        User u = new User();
        u.setNick(login);
        Hasher hasher = HasherFactory.createDefaultHasher();
        u.setSalt(HasherFactory.generateSalt());
        hasher.setSalt(u.getSalt());
        u.setPasswordHash(hasher.getHash(password.getBytes()));
        u.setCreatedAt(new Date());
        u.setKey(ElGamalAsymKeyFactory.create(false));

        SyncManager<User> em = new UserSyncManagerImpl();
        em.begin();
        em.persist(u);
        em.end();

        Authentifier auth = Application.getInstance().getAuth();
        LoginToken token = new LoginToken();
        token.setToken(auth.getToken(login, password));
        token.setUserid(u.getId());
        JsonTools<LoginToken> json = new JsonTools<>(new TypeReference<LoginToken>(){});
        return json.toJson(token);
    }

    /*@POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)*/
    public String add(User user) {

        return null;
    }

    /*@GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON) */
    //public String get(@PathParam("id") String id) {
    public String get(String id) {
        SyncManager<User> em = new UserSyncManagerImpl();
        JsonTools<User> json = new JsonTools<>(new TypeReference<User>(){});
        return json.toJson(em.findOneById(id));
    }

    /* @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON) */
    public String get() {
        SyncManager<User> em = new UserSyncManagerImpl();
        JsonTools<Collection<User>> json = new JsonTools<>(new TypeReference<Collection<User>>(){});
        return json.toJson(em.findAll());
        //return JsonUtils.collectionStringify(em.findAll());
    }

    /*@PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)*/
    public String edit(User user) {

        return null;
    }

    /*@DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON) */
    //public String delete(@PathParam("id") long id) {
    public String delete(long id) {
        return null;
    }
}
