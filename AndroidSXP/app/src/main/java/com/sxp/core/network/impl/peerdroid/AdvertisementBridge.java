package com.sxp.core.network.impl.peerdroid;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Map;

import org.jdom2.Element;

import net.jxta.document.Advertisement;
import net.jxta.document.Attributable;
import net.jxta.document.Document;
import net.jxta.document.MimeMediaType;
import net.jxta.document.StructuredDocument;
import net.jxta.document.StructuredDocumentFactory;
import net.jxta.document.TextElement;
import net.jxta.id.ID;

public class AdvertisementBridge extends Advertisement{
    private com.sxp.core.network.api.advertisement.Advertisement advertisement;

    public AdvertisementBridge() { }

    public AdvertisementBridge(com.sxp.core.network.api.advertisement.Advertisement adv) {
        super();
        this.advertisement = adv;
    }

    /**
     * Create a new AdvertisementBridge instance initialized with a Jxta xml root element.
     * @param root
     */
    public AdvertisementBridge(@SuppressWarnings("rawtypes") Element root) {
        super();
        @SuppressWarnings("rawtypes")
        TextElement doc = (TextElement) root;
        @SuppressWarnings("rawtypes")
        TextElement className = (TextElement) doc.getChildren("advertisementClass").nextElement();
        try {
            //try to find the class used for this advertisement
            Class<?> adv = Class.forName(className.getValue().toString());
            Constructor<?> cons = adv.getConstructor();
            this.advertisement = (com.sxp.core.network.api.advertisement.Advertisement) cons.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException |
                InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        Element rootElement = new Element("root");
        @SuppressWarnings("rawtypes")
        Enumeration elements = doc.getChildren();
        while(elements.hasMoreElements()) {
            @SuppressWarnings("rawtypes")
            TextElement elem = (TextElement) elements.nextElement();
            if(elem.getName().equals("advertisementClass")) {
                continue;
            }
            Element e = new Element(elem.getName()); //convert into a Jdom element.
            e.addContent(elem.getValue().toString());
            rootElement.addContent(e);
        }
        this.advertisement.initialize(new org.jdom2.Document(rootElement));
    }

    /**
     * {@inheritDoc}
     * @param asMimeType
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Document getDocument(MimeMediaType asMimeType) {
        @SuppressWarnings("rawtypes")
        StructuredDocument adv = StructuredDocumentFactory.newStructuredDocument(asMimeType, getAdvType());
        if (adv instanceof Attributable) {
            ((Attributable) adv).addAttribute("xmlns:jxta", "http://jxta.org");
        }

        for(Element e : this.advertisement.getDocument().getRootElement().getChildren()) {
            @SuppressWarnings("rawtypes")
            net.jxta.document.Element e1 = adv.createElement(e.getName(), e.getValue());
            adv.appendChild(e1);
        }
        return adv;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public ID getID() {
        // TODO see if we generate id for advs
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getIndexFields() {
        if(advertisement == null) {
            throw new RuntimeException("Advertisement is null");
        }
        return advertisement.getIndexFields();
    }

    @Override
    public Map getIndexMap() {
        return null;
    }

    @Override
    public String getAdvType() {
        return "jxta:" + this.getClass().getName();
    }

    public com.network.api.advertisement.Advertisement getAdvertisement() {
        return advertisement;
    }
}
