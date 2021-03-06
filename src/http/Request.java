package http;

import http.headers.*;
import java.util.ArrayList;

/**
 * Correspond à une requete HTTP
 * @author Pierre-Emmanuel Pourquier
 * @version 1.0
 */
public class Request {
    private Header header; //correspond au header de la requete http
    private Content content; //correspond au contenu de la requete http
    private ArrayList<String> request; // correspond à la chaine brute à analyser
    
    /**
     * initialise l'objet
     * @param pRequete la chaine de caractères à analyser qui correspond à une requete http
     */
    public Request(ArrayList<String> pRequete){
        this.request = pRequete;
        this.header = new Header();
        this.content = new Content();
        analyseRequest();
    }
    
    /**
     * parse request pour créer un header et un content
     */
    private void analyseRequest(){
        //première ligne : action
        String premierHead = request.get(0);
        System.out.println(premierHead);
        this.header.setAction(parseActionHTTP(premierHead));
        this.header.setCible(parseCibleHTTP(premierHead));
        this.header.setVersion(parseVersionHTTP(premierHead));
        
        System.out.println("requete parsée dans le header : \naction : "+ this.header.getAction() + "\ncible : "+this.header.getCible() + "\nversion : " + this.header.getVersion());
                
                
        for(int i = 1; i< request.size();i++){
            if(request.get(i).startsWith("Host:")){
                this.header.setHost(parseHostHTTP(request.get(i)));
            }
            else{
             System.out.println("a parser : "+request.get(i));
            }
        }
    }

    /**
     * vérifie si la requete nécessite du contenu
     * @return true si besoin d'un content 
     */
    public boolean besoinContent() {
      //if(header.getContentLength > 0){
      //    return true;
      //}else{
        return false;//si il y a du contenu. Je l'ai mis a des fins de tests
      //}
    }
    
    /**
     * analyse le contenu
     * @param pContenu le contenu au format string de la requete http initiale
     */
    public void analyseContent(String pContenu){
        
    }
    
    
    /**
     * @return le header de la requete
     */
    public Header getHeader() {
        return header;
    }

    /**
     * @return le contenu de la requete
     */
    public Content getContent() {
        return content;
    }
    
    /**
     * ajoute le contenu de la requete
     * @param pcontent le contenu a ajouter
     */
    public void setContent(Content pcontent){
        this.content = pcontent;
    }
    
    
    /**
     * parse la première ligne d'une requete http et en extrait l'action (GET, POST, HEAD...)
     * @param pHeaderHTTP la première ligne du header contenant l'action
     * @return le type d'action | null si aucune action
     */
    private Action parseActionHTTP(String pHeaderHTTP){
        Action action=null;
        String headerHTTP = pHeaderHTTP.substring(0,pHeaderHTTP.indexOf(" ")); 
         //TODO : vérifier que tout le monde est en 1.7
        //TODO : remplacer apr un switch sur STRING
        if(headerHTTP.equals("GET")){action = Action.GET;}else{
        if(headerHTTP.equals("POST")){action = Action.POST;}else{
        if(headerHTTP.equals("HEAD")){action = Action.HEAD;}else{
        if(headerHTTP.equals("CONNECT")){action = Action.CONNECT;}else{
        if(headerHTTP.equals("DELETE")){action = Action.DELETE;}else{
        if(headerHTTP.equals("OPTIONS")){action = Action.OPTIONS;}else{
        if(headerHTTP.equals("PATCH")){action = Action.PATCH;}else{
        if(headerHTTP.equals("PUT")){action = Action.PUT;}else{
        if(headerHTTP.equals("TRACE")){action = Action.TRACE;}}}}}}}}}
        return action;
    }
    
    /**
     * parse la première enête http et extrait la cible de celle-ci
     * @param pHeaderHTTP la première entête d'une requête HTTP
     * @return la cible de la requête http
     */
    private String parseCibleHTTP(String pHeaderHTTP){
        String cible="";
        String headerHTTP = pHeaderHTTP.substring(pHeaderHTTP.indexOf("/"));
        cible = headerHTTP.substring(0,headerHTTP.indexOf(" "));
        return cible;
    }
    
    private Version parseVersionHTTP(String pHeaderHTTP){
        Version version = http.headers.Version.HTTP_0_9;
        String header = pHeaderHTTP.substring(pHeaderHTTP.indexOf("HTTP/"));
        if(header.equals("HTTP/1.1")){version = Version.HTTP_1_1;}else{
        if(header.equals("HTTP/1.0")){version = Version.HTTP_1_0;}else{
        if(header.equals("HTTP/0.9")){version = Version.HTTP_0_9;}}}
        return version;
    }
    
    private Host parseHostHTTP(String pHeaderHTTP){
        Host host = new Host();
        String strHost = pHeaderHTTP.substring(pHeaderHTTP.indexOf(":"));
        return host;
    }
}
