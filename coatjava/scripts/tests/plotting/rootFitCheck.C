double fitFunc(double *x, double par[]){
	double xx = *x;
	double func = par[0]*TMath::Gaus(xx,par[1],par[2]) + par[3] + par[4]*xx;
	return func;
}

void rootFitCheck(){

	gRandom->SetSeed(0);
	
	TF1 *f1 = new TF1("f1",fitFunc,1.0,34.0,5);
	f1->SetParameter(0,1000.0);
	f1->SetParameter(1,15.5);
	f1->SetParameter(2,2.8);
	f1->SetParameter(3,500.0);
	f1->SetParameter(4,-12.0);
	for(int loop = 0; loop < f1->GetNpar();loop++){
		cout << loop << "  " << f1->GetParameter(loop) << endl;
	}
	f1->Draw();
	TH1D *H1 = new TH1D("H1","",120,0.0,35.0);
	for(int loop = 0; loop < 2500; loop++){
		H1->Fill(f1->GetRandom());
	}
	H1->Draw();
	TF1 *f2 = new TF1("f2",fitFunc,5.0,30.0,5);
	f2->SetParameter(0,100.0);	
	f2->SetParameter(1,15.0);	
	f2->SetParameter(2,3.0);
	H1->Fit(f2,"RME");
	cout << f2->GetNDF() << "  " << f2->GetChisquare() << endl;
}
