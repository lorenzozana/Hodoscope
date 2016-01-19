{

  double x[] = {-32.34,-35.69,-56.22};
  double y[] = {-59.38,-87.94,78.05};

  TGraph *graph = new TGraph(3,x,y);
  graph->SetMarkerStyle(20);
  TH2D *H2 = new TH2D("H2","",1,-100,100,1,-100,100);
  H2->Draw();
  graph->Draw("P");
}
