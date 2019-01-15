package com.jmc.backend.ventas.apirest.auth.auth;

public class JwtConfig {

	public static final String LLAVE_SECRETA=".Desarrollo2019.app";
	public static final String RSA_RIVATE="-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEpAIBAAKCAQEA40wnEXaRbTa2HXapnKfjJJCFpVI1PHrJfaHLITjoRfe3PKYx\r\n" + 
			"fjFyODQRy6vUqAVfltAiCvNWnUhDenZTF8odhDR4ccycWPhDULxKrhL4d7b0EvLz\r\n" + 
			"Wf6W1vUM+pSE286gvljA5UF9qC2EP6PHopEpL25VMfqQ61d/M6WRCav0SZe6CeHr\r\n" + 
			"k/vc7RABxjCDxh8lU70acOFLAsYcwbnpEhD56bMYzN9Aignu/lK6iTk27Yt6wHiL\r\n" + 
			"Jl3pEPL34gJAXi7ecGpE7pE/+VG6hfp0/3kSJpvf8jpGa544Sx6x9eWLGzavfwWD\r\n" + 
			"WMMBNVEikZJ6EStBBg1dybsYRhfN7xsChdQBRwIDAQABAoIBAGO2LpVcnGN60t2O\r\n" + 
			"Bs4wYFbUB60EqVIE04uHhKLoYgz/pKS4i5G+g0rgnMXqdXnYEZ0JZUXEhkStRNze\r\n" + 
			"N4mCIvyyP/w8NIJbNryePWyr6dAqtFvztInQQ7+89JqguL/sn1uhd82as1Mp1JBO\r\n" + 
			"eu94Gscx8bHhJ932Hlk70qqZgqsdTpfgCgL9VFYWpUhnS6tNNlSdVkjY5fWypMeW\r\n" + 
			"Wnqf7cS30vKMcTYca0sdQJts8hvH0Dpj84NLYDCZTKOpuNvqW/3Io1LN2DPCJvf3\r\n" + 
			"uYzBdNyRFZC+rUnwC6rJh6wso/WltXakjJ5VtEuug3XY0/9PBJ9tEOaTVk0EgK8M\r\n" + 
			"WyqiFpECgYEA/bZnhr/eKS/TwVlQUnj7lvu+gTBbFw7kMA+pV73+APA8bkiQcq5i\r\n" + 
			"YFAgAwjB27ls4UegVNtdHrVAaKKKeB41zNODZccyEd7MxJ2w/ZX0u1BO6EA2+vVj\r\n" + 
			"kdnU1z/TYJYDJn3E34HVoi6B11aFCOiAxyNMmgYV4lCJcBMIo5ZG+bMCgYEA5VjH\r\n" + 
			"ismdYO8XSVH/TcEZ7L/JXEX/r8n9uD8001q2j0q6bl+gmgMRhj5IjYC8wspRmoDN\r\n" + 
			"sCEFI++oHNYXM3Yi2UzvaPZjMiZSyXk+W64Enl/bAi+rOwBJvNalGhzgtcywOMYJ\r\n" + 
			"2WZ2Yrx8oi/0guCVyHQp7BGJ2UwihRQSIy3DaB0CgYEAmGKtbbbiNfS/CBuf7dky\r\n" + 
			"q63K74devsu37NILUWMRI/knIso80FTDS7v0bj4dSd7mcaz2tOEI3j/ZTr+J0bm7\r\n" + 
			"cKn9+uyYuzkJ3nIkcztVQghrTH39R+CJjHm8qAhy6klP3RefXut5qC6s1+zkfMRB\r\n" + 
			"TNp70Osky8i5s/IKt060lJcCgYEAgjIH9Q3HPcP84C6gVC+O3Atz7+H86Hougm2/\r\n" + 
			"O0x/3pB5jTbUIRok1KR+43V1ss7PzHnaSmY0WNYwBU60FQ63ccxvDJLydQG0dZNS\r\n" + 
			"hxEephmcZn1RI0liksT3LP+/wPTWBSTHapb0FdESn6/Bipqb7ROkyNKcArNp1Uoz\r\n" + 
			"kazlBTECgYADeWFrvy1p1URj+Qu8ItOu5yz+rZnJ9/2x8fBIFTgsR99j1wSAqt0q\r\n" + 
			"2SwSSdM8mxvydrsa5FFJ5KY0/NhpcoOIseWpqF4JVrpIxwj+PlJJsbqsD4r9ihsc\r\n" + 
			"q1K2sG5zaCc7fbuxwa8CR3EDt0+A3sLRhTfpQOOgme/JmwDYLsj1xw==\r\n" + 
			"-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLIC="-----BEGIN PUBLIC KEY-----\r\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA40wnEXaRbTa2HXapnKfj\r\n" + 
			"JJCFpVI1PHrJfaHLITjoRfe3PKYxfjFyODQRy6vUqAVfltAiCvNWnUhDenZTF8od\r\n" + 
			"hDR4ccycWPhDULxKrhL4d7b0EvLzWf6W1vUM+pSE286gvljA5UF9qC2EP6PHopEp\r\n" + 
			"L25VMfqQ61d/M6WRCav0SZe6CeHrk/vc7RABxjCDxh8lU70acOFLAsYcwbnpEhD5\r\n" + 
			"6bMYzN9Aignu/lK6iTk27Yt6wHiLJl3pEPL34gJAXi7ecGpE7pE/+VG6hfp0/3kS\r\n" + 
			"Jpvf8jpGa544Sx6x9eWLGzavfwWDWMMBNVEikZJ6EStBBg1dybsYRhfN7xsChdQB\r\n" + 
			"RwIDAQAB\r\n" + 
			"-----END PUBLIC KEY-----";
}
