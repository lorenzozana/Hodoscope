{
  TFile *file000 = TFile::Open("analysis_output000.root");
  TFile *file075 = TFile::Open("analysis_output075.root");
  TFile *file100 = TFile::Open("analysis_output100.root");
  TFile *file150 = TFile::Open("analysis_output150.root");
  TFile *file200 = TFile::Open("analysis_output200.root");

  TH1F *h1_Edep_thin_000 = (TH1F*)file000->Get("h1_Edep_thin");
  h1_Edep_thin_000->SetLineColor(6);
  TH1F *h1_Edep_thin_075 = (TH1F*)file075->Get("h1_Edep_thin");
  h1_Edep_thin_075->SetLineColor(1);
  TH1F *h1_Edep_thin_100 = (TH1F*)file100->Get("h1_Edep_thin");
  h1_Edep_thin_100->SetLineColor(2);
  TH1F *h1_Edep_thin_150 = (TH1F*)file150->Get("h1_Edep_thin");
  h1_Edep_thin_150->SetLineColor(3);
  TH1F *h1_Edep_thin_200 = (TH1F*)file200->Get("h1_Edep_thin");
  h1_Edep_thin_200->SetLineColor(4);

  TH1F *h1_Edep_thick_000 = (TH1F*)file000->Get("h1_Edep_thick");
  h1_Edep_thick_000->SetLineColor(6);
  TH1F *h1_Edep_thick_075 = (TH1F*)file075->Get("h1_Edep_thick");
  h1_Edep_thick_075->SetLineColor(1);
  TH1F *h1_Edep_thick_100 = (TH1F*)file100->Get("h1_Edep_thick");
  h1_Edep_thick_100->SetLineColor(2);
  TH1F *h1_Edep_thick_150 = (TH1F*)file150->Get("h1_Edep_thick");
  h1_Edep_thick_150->SetLineColor(3);
  TH1F *h1_Edep_thick_200 = (TH1F*)file200->Get("h1_Edep_thick");
  h1_Edep_thick_200->SetLineColor(4);

  TH2F *h2_theta_z_000 = (TH2F*)file000->Get("h2_theta_z");
  TH2F *h2_theta_z_075 = (TH2F*)file075->Get("h2_theta_z");
  TH2F *h2_theta_z_100 = (TH2F*)file100->Get("h2_theta_z");
  TH2F *h2_theta_z_150 = (TH2F*)file150->Get("h2_theta_z");
  TH2F *h2_theta_z_200 = (TH2F*)file200->Get("h2_theta_z");

  h2_theta_z_000->SetTitle("#theta e^{-} at target vs z (0.000mm)");
  h2_theta_z_075->SetTitle("#theta e^{-} at target vs z (0.075mm)");
  h2_theta_z_100->SetTitle("#theta e^{-} at target vs z (0.100mm)");
  h2_theta_z_150->SetTitle("#theta e^{-} at target vs z (0.150mm)");
  h2_theta_z_200->SetTitle("#theta e^{-} at target vs z (0.200mm)");
  

  TH2F *h2_Edep_thin_z_000 = (TH2F*)file000->Get("h2_Edep_thin_z");
  TH2F *h2_Edep_thin_z_075 = (TH2F*)file075->Get("h2_Edep_thin_z");
  TH2F *h2_Edep_thin_z_100 = (TH2F*)file100->Get("h2_Edep_thin_z");
  TH2F *h2_Edep_thin_z_150 = (TH2F*)file150->Get("h2_Edep_thin_z");
  TH2F *h2_Edep_thin_z_200 = (TH2F*)file200->Get("h2_Edep_thin_z");

  h2_Edep_thin_z_000->SetTitle("Energy deposited Thin Tyles (MeV) vs Z for R_{hit} < 173mm  (0.000mm)");
  h2_Edep_thin_z_075->SetTitle("Energy deposited Thin Tyles (MeV) vs Z for R_{hit} < 173mm  (0.075mm)");
  h2_Edep_thin_z_100->SetTitle("Energy deposited Thin Tyles (MeV) vs Z for R_{hit} < 173mm  (0.100mm)");
  h2_Edep_thin_z_150->SetTitle("Energy deposited Thin Tyles (MeV) vs Z for R_{hit} < 173mm  (0.150mm)");
  h2_Edep_thin_z_200->SetTitle("Energy deposited Thin Tyles (MeV) vs Z for R_{hit} < 173mm  (0.200mm)");


  TH2F *h2_Edep_thick_z_000 = (TH2F*)file000->Get("h2_Edep_thick_z");
  TH2F *h2_Edep_thick_z_075 = (TH2F*)file075->Get("h2_Edep_thick_z");
  TH2F *h2_Edep_thick_z_100 = (TH2F*)file100->Get("h2_Edep_thick_z");
  TH2F *h2_Edep_thick_z_150 = (TH2F*)file150->Get("h2_Edep_thick_z");
  TH2F *h2_Edep_thick_z_200 = (TH2F*)file200->Get("h2_Edep_thick_z");

  h2_Edep_thick_z_000->SetTitle("Energy deposited Thick Tyles (MeV) vs Z for R_{hit} < 173mm  (0.000mm)");
  h2_Edep_thick_z_075->SetTitle("Energy deposited Thick Tyles (MeV) vs Z for R_{hit} < 173mm  (0.075mm)");
  h2_Edep_thick_z_100->SetTitle("Energy deposited Thick Tyles (MeV) vs Z for R_{hit} < 173mm  (0.100mm)");
  h2_Edep_thick_z_150->SetTitle("Energy deposited Thick Tyles (MeV) vs Z for R_{hit} < 173mm  (0.150mm)");
  h2_Edep_thick_z_200->SetTitle("Energy deposited Thick Tyles (MeV) vs Z for R_{hit} < 173mm  (0.200mm)");


  TH2F *h2_Edep_thin_XY_000 = (TH2F*)file000->Get("h2_Edep_thin_XY");
  TH2F *h2_Edep_thin_XY_075 = (TH2F*)file075->Get("h2_Edep_thin_XY");
  TH2F *h2_Edep_thin_XY_100 = (TH2F*)file100->Get("h2_Edep_thin_XY");
  TH2F *h2_Edep_thin_XY_150 = (TH2F*)file150->Get("h2_Edep_thin_XY");
  TH2F *h2_Edep_thin_XY_200 = (TH2F*)file200->Get("h2_Edep_thin_XY");

  h2_Edep_thin_XY_000->SetTitle("THIN Layer Hit 2Dim weighted with Edep (0.000mm)");
  h2_Edep_thin_XY_075->SetTitle("THIN Layer Hit 2Dim weighted with Edep (0.075mm)");
  h2_Edep_thin_XY_100->SetTitle("THIN Layer Hit 2Dim weighted with Edep (0.100mm)");
  h2_Edep_thin_XY_150->SetTitle("THIN Layer Hit 2Dim weighted with Edep (0.150mm)");
  h2_Edep_thin_XY_200->SetTitle("THIN Layer Hit 2Dim weighted with Edep (0.200mm)");

  h2_Edep_thin_XY_000->SetMaximum(50);
  h2_Edep_thin_XY_075->SetMaximum(50);
  h2_Edep_thin_XY_100->SetMaximum(50);
  h2_Edep_thin_XY_150->SetMaximum(50);
  h2_Edep_thin_XY_200->SetMaximum(50);


  TH2F *h2_Edep_thick_XY_000 = (TH2F*)file000->Get("h2_Edep_thick_XY");
  TH2F *h2_Edep_thick_XY_075 = (TH2F*)file075->Get("h2_Edep_thick_XY");
  TH2F *h2_Edep_thick_XY_100 = (TH2F*)file100->Get("h2_Edep_thick_XY");
  TH2F *h2_Edep_thick_XY_150 = (TH2F*)file150->Get("h2_Edep_thick_XY");
  TH2F *h2_Edep_thick_XY_200 = (TH2F*)file200->Get("h2_Edep_thick_XY");

  h2_Edep_thick_XY_000->SetTitle("THICK Layer Hit 2Dim weighted with Edep (0.000mm)");
  h2_Edep_thick_XY_075->SetTitle("THICK Layer Hit 2Dim weighted with Edep (0.075mm)");
  h2_Edep_thick_XY_100->SetTitle("THICK Layer Hit 2Dim weighted with Edep (0.100mm)");
  h2_Edep_thick_XY_150->SetTitle("THICK Layer Hit 2Dim weighted with Edep (0.150mm)");
  h2_Edep_thick_XY_200->SetTitle("THICK Layer Hit 2Dim weighted with Edep (0.200mm)");

  h2_Edep_thick_XY_000->SetMaximum(80);
  h2_Edep_thick_XY_075->SetMaximum(80);
  h2_Edep_thick_XY_100->SetMaximum(80);
  h2_Edep_thick_XY_150->SetMaximum(80);
  h2_Edep_thick_XY_200->SetMaximum(80);


  TCanvas *c1 = new TCanvas("c1","c1",800,600);
  c1->SetLogy();
  TLegend *leg = new TLegend(0.6,0.5,0.98,0.8);
  leg->AddEntry(h1_Edep_thick_000,"0.000mm paint","l");
  leg->AddEntry(h1_Edep_thick_075,"0.075mm paint","l");
  leg->AddEntry(h1_Edep_thick_100,"0.100mm paint","l");
  leg->AddEntry(h1_Edep_thick_150,"0.150mm paint","l");
  leg->AddEntry(h1_Edep_thick_200,"0.200mm paint","l");

  gStyle->SetOptStat(0);

  h1_Edep_thick_075->Draw();
  h1_Edep_thick_000->Draw("SAME");
  h1_Edep_thick_100->Draw("SAME");
  h1_Edep_thick_150->Draw("SAME");
  h1_Edep_thick_200->Draw("SAME");

  leg->Draw();

  c1->Print("pictures/Edep_thick_tiles.pdf");
  c1->Print("pictures/Edep_thick_tiles.png");

  h1_Edep_thin_075->Draw();
  h1_Edep_thin_000->Draw("SAME");
  h1_Edep_thin_100->Draw("SAME");
  h1_Edep_thin_150->Draw("SAME");
  h1_Edep_thin_200->Draw("SAME");
  leg->Draw();

  c1->Print("pictures/Edep_thin_tiles.pdf");
  c1->Print("pictures/Edep_thin_tiles.png");

  gStyle->SetOptStat(1);

  TCanvas *c2 = new TCanvas("c2","c2",800,1200);
  c2->Divide(2,3);
  c2->SetLogz();
  c2->cd(1);
  c2->cd(1)->SetLogz();
  h2_theta_z_075->Draw("COLZ");
  c2->cd(2);
  c2->cd(2)->SetLogz();
  h2_theta_z_100->Draw("COLZ");
  c2->cd(3);
  c2->cd(3)->SetLogz();
  h2_theta_z_150->Draw("COLZ");
  c2->cd(4);
  c2->cd(4)->SetLogz();
  h2_theta_z_200->Draw("COLZ");
  c2->cd(5);
  c2->cd(5)->SetLogz();
  h2_theta_z_000->Draw("COLZ");


  c2->Print("pictures/Theta_Z.pdf");
  c2->Print("pictures/Theta_Z.png");

  c2->cd(1);
  h2_Edep_thin_z_075->Draw("COLZ");
  c2->cd(2);
  h2_Edep_thin_z_100->Draw("COLZ");
  c2->cd(3);
  h2_Edep_thin_z_150->Draw("COLZ");
  c2->cd(4);
  h2_Edep_thin_z_200->Draw("COLZ");
  c2->cd(5);
  h2_Edep_thin_z_000->Draw("COLZ");

  c2->Print("pictures/Edep_Thin_Z.pdf");
  c2->Print("pictures/Edep_Thin_Z.png");

  c2->cd(1);
  h2_Edep_thick_z_075->Draw("COLZ");
  c2->cd(2);
  h2_Edep_thick_z_100->Draw("COLZ");
  c2->cd(3);
  h2_Edep_thick_z_150->Draw("COLZ");
  c2->cd(4);
  h2_Edep_thick_z_200->Draw("COLZ");
  c2->cd(5);
  h2_Edep_thick_z_000->Draw("COLZ");

  c2->Print("pictures/Edep_Thick_Z.pdf");
  c2->Print("pictures/Edep_Thick_Z.png");

  c2->cd(1);
  c2->cd(1)->SetLogz(0);
  h2_Edep_thick_XY_075->Draw("COLZ");
  c2->cd(2);
  c2->cd(2)->SetLogz(0);
  h2_Edep_thick_XY_100->Draw("COLZ");
  c2->cd(3);
  c2->cd(3)->SetLogz(0);
  h2_Edep_thick_XY_150->Draw("COLZ");
  c2->cd(4);
  c2->cd(4)->SetLogz(0);
  h2_Edep_thick_XY_200->Draw("COLZ");
  c2->cd(5);
  c2->cd(5)->SetLogz(0);
  h2_Edep_thick_XY_000->Draw("COLZ");

  c2->Print("pictures/Edep_thick_XY.pdf");
  c2->Print("pictures/Edep_thick_XY.png");


  c2->cd(1);
  c2->cd(1)->SetLogz(0);
  h2_Edep_thin_XY_075->Draw("COLZ");
  c2->cd(2);
  c2->cd(2)->SetLogz(0);
  h2_Edep_thin_XY_100->Draw("COLZ");
  c2->cd(3);
  c2->cd(3)->SetLogz(0);
  h2_Edep_thin_XY_150->Draw("COLZ");
  c2->cd(4);
  c2->cd(4)->SetLogz(0);
  h2_Edep_thin_XY_200->Draw("COLZ");
  c2->cd(5);
  c2->cd(5)->SetLogz(0);
  h2_Edep_thin_XY_000->Draw("COLZ");

  c2->Print("pictures/Edep_thin_XY.pdf");
  c2->Print("pictures/Edep_thin_XY.png");

  double count_000 = h1_Edep_thick_000->GetEntries();
  double count_100 = h1_Edep_thick_100->GetEntries();
  double count_150 = h1_Edep_thick_150->GetEntries();
  double count_200 = h1_Edep_thick_200->GetEntries();

  double x[4] = {0.0,0.100, 0.150, 0.20};
  double ex[4] = {0.0,0.0,0.0,0.0};
  double y[4] ={1.,count_100/count_000,count_150/count_000,count_200/count_000};
  double ey[4] = {pow(2./count_000,0.5),y[1] * pow(1./count_000+1./count_100,0.5),y[1] * pow(1./count_000+1./count_150,0.5),y[1] * pow(1./count_000+1./count_200,0.5)};


  TGraphErrors *gr = new TGraphErrors(4,x,y,ex,ey);
  gr->GetXaxis()->SetTitle("Paint thickness (mm)");
  gr->GetYaxis()->SetTitle("Efficiency");
  gr->GetXaxis()->SetRangeUser(-0.03,0.22);
  gr->SetMarkerStyle(24);
  c1->cd();
  c1->SetLogy(0);
  gr->Fit("pol1");
  TF1 *f3 = gr->GetFunction("pol1");
  char Title[60];
  sprintf(Title,"Efficiency vs paint thickness = 1.0 %f * thickness(mm)",f3->GetParameter(1));
  gr->SetTitle(Title);
  gr->Draw("APE");

  c1->Print("pictures/Efficiency_single_thick.pdf");

  double count2_000 = h1_Edep_thin_000->GetEntries();
  double count2_100 = h1_Edep_thin_100->GetEntries();
  double count2_150 = h1_Edep_thin_150->GetEntries();
  double count2_200 = h1_Edep_thin_200->GetEntries();

  double x2[4] = {0.0,0.100, 0.150, 0.20};
  double ex2[4] = {0.0,0.0,0.0,0.0};
  double y2[4] ={1.,count2_100/count2_000,count2_150/count2_000,count2_200/count2_000};
  double ey2[4] = {pow(2./count2_000,0.5),y[1] * pow(1./count2_000+1./count2_100,0.5),y[1] * pow(1./count2_000+1./count2_150,0.5),y[1] * pow(1./count2_000+1./count2_200,0.5)};


  gr = new TGraphErrors(4,x2,y2,ex2,ey2);
  gr->GetXaxis()->SetTitle("Paint thickness (mm)");
  gr->GetYaxis()->SetTitle("Efficiency");
  gr->GetXaxis()->SetRangeUser(-0.03,0.22);
  gr->SetMarkerStyle(24);
  c1->cd();
  c1->SetLogy(0);
  gr->Fit("pol1");
  f3 = gr->GetFunction("pol1");
  sprintf(Title,"Efficiency vs paint thickness = 1.0 %.3f * thickness(mm)",f3->GetParameter(1));
  gr->SetTitle(Title);
 
  gr->Draw("APE");
  
  c1->Print("pictures/Efficiency_single_thin.pdf");

  h2_Edep_thick_XY_150->Draw("COLZ");
  c1->Print("pictures/Efficiency_XY_thick_150.pdf");

  h2_Edep_thin_XY_150->Draw("COLZ");
  c1->Print("pictures/Efficiency_XY_thin_150.pdf");

}
