//********************************************************************
// Translates BMT table from user format to standard
//********************************************************************
filename = args[0];
BufferedReader stream = new BufferedReader(new FileReader(filename));
while (stream.ready()) {
	String s = stream.readLine();
	//System.out.println(s);
	if(s.startsWith("#")==true) continue;
	String[] tokens = s.split("\\s+");
	if(tokens.length>=6){
		String entry =  String.format("BMT %8d %8d %8d %8d %8d %8d %8d", 
			Integer.parseInt(tokens[3]),Integer.parseInt(tokens[4]),Integer.parseInt(tokens[5]),
			Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),0);
		System.out.println(entry);
	}	
}
