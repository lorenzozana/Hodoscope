Double_t fitFunc(double *x, double *p){
  double xx = *x;
  double result = p[0]*TMath::Gaus(x,p[1],p[2]);
}

void rootFit(){

  TCanvas *c1 = new TCanvas("c1","",600,600);
  c1->Divide(1,2);
  TNtuple *T = new TNtuple("T","","x:y:z");
  T->ReadFile("histogramData.dat");
  T->Draw("y:x");

  Double_t *X = T->GetV1();
  Double_t *Y = T->GetV2();

  for(int loop = 0; loop < 120; loop++){
    cout << X[loop] << "  " << Y[loop] << endl;
  }

  c1->Clear();
  TGraph *graphT = new TGraph(120,Y,X);
  TGraph *graphB = new TGraph(120,Y,X);
  c1->cd(1);
  graphT->Draw("APL");
  graphT->Fit("gaus+pol2","RME");

}
