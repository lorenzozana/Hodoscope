{
  TFile *file075 = TFile::Open("analysis_output075.root");
  TFile *file100 = TFile::Open("analysis_output100.root");
  TFile *file150 = TFile::Open("analysis_output150.root");
  TFile *file200 = TFile::Open("analysis_output200.root");

  TH1F *h1_Edep_thin_075 = (TH1F*)file075->Get("h1_Edep_thin");
  h1_Edep_thin_075->SetLineColor(1);
  TH1F *h1_Edep_thin_100 = (TH1F*)file100->Get("h1_Edep_thin");
  h1_Edep_thin_100->SetLineColor(2);
  TH1F *h1_Edep_thin_150 = (TH1F*)file150->Get("h1_Edep_thin");
  h1_Edep_thin_150->SetLineColor(3);
  TH1F *h1_Edep_thin_200 = (TH1F*)file200->Get("h1_Edep_thin");
  h1_Edep_thin_200->SetLineColor(4);

  TH1F *h1_Edep_thick_075 = (TH1F*)file075->Get("h1_Edep_thick");
  h1_Edep_thick_075->SetLineColor(1);
  TH1F *h1_Edep_thick_100 = (TH1F*)file100->Get("h1_Edep_thick");
  h1_Edep_thick_100->SetLineColor(2);
  TH1F *h1_Edep_thick_150 = (TH1F*)file150->Get("h1_Edep_thick");
  h1_Edep_thick_150->SetLineColor(3);
  TH1F *h1_Edep_thick_200 = (TH1F*)file200->Get("h1_Edep_thick");
  h1_Edep_thick_200->SetLineColor(4);

  TH2F *h2_theta_z_075 = (TH2F*)file075->Get("h2_theta_z");
  TH2F *h2_theta_z_100 = (TH2F*)file100->Get("h2_theta_z");
  TH2F *h2_theta_z_150 = (TH2F*)file150->Get("h2_theta_z");
  TH2F *h2_theta_z_200 = (TH2F*)file200->Get("h2_theta_z");

  h2_theta_z_075->SetTitle("#theta e^{-} at target vs z (0.075mm)");
  h2_theta_z_100->SetTitle("#theta e^{-} at target vs z (0.100mm)");
  h2_theta_z_150->SetTitle("#theta e^{-} at target vs z (0.150mm)");
  h2_theta_z_200->SetTitle("#theta e^{-} at target vs z (0.200mm)");
  

  TH2F *h2_Edep_thin_z_075 = (TH2F*)file075->Get("h2_Edep_thin_z");
  TH2F *h2_Edep_thin_z_100 = (TH2F*)file100->Get("h2_Edep_thin_z");
  TH2F *h2_Edep_thin_z_150 = (TH2F*)file150->Get("h2_Edep_thin_z");
  TH2F *h2_Edep_thin_z_200 = (TH2F*)file200->Get("h2_Edep_thin_z");

  h2_Edep_thin_z_075->SetTitle("Energy deposited Thin Tyles (MeV) vs Z for R_{hit} < 173mm  (0.075mm)");
  h2_Edep_thin_z_100->SetTitle("Energy deposited Thin Tyles (MeV) vs Z for R_{hit} < 173mm  (0.100mm)");
  h2_Edep_thin_z_150->SetTitle("Energy deposited Thin Tyles (MeV) vs Z for R_{hit} < 173mm  (0.150mm)");
  h2_Edep_thin_z_200->SetTitle("Energy deposited Thin Tyles (MeV) vs Z for R_{hit} < 173mm  (0.200mm)");


  TH2F *h2_Edep_thick_z_075 = (TH2F*)file075->Get("h2_Edep_thick_z");
  TH2F *h2_Edep_thick_z_100 = (TH2F*)file100->Get("h2_Edep_thick_z");
  TH2F *h2_Edep_thick_z_150 = (TH2F*)file150->Get("h2_Edep_thick_z");
  TH2F *h2_Edep_thick_z_200 = (TH2F*)file200->Get("h2_Edep_thick_z");

  h2_Edep_thick_z_075->SetTitle("Energy deposited Thick Tyles (MeV) vs Z for R_{hit} < 173mm  (0.075mm)");
  h2_Edep_thick_z_100->SetTitle("Energy deposited Thick Tyles (MeV) vs Z for R_{hit} < 173mm  (0.100mm)");
  h2_Edep_thick_z_150->SetTitle("Energy deposited Thick Tyles (MeV) vs Z for R_{hit} < 173mm  (0.150mm)");
  h2_Edep_thick_z_200->SetTitle("Energy deposited Thick Tyles (MeV) vs Z for R_{hit} < 173mm  (0.200mm)");


  TH2F *h2_Edep_thin_XY_075 = (TH2F*)file075->Get("h2_Edep_thin_XY");
  TH2F *h2_Edep_thin_XY_100 = (TH2F*)file100->Get("h2_Edep_thin_XY");
  TH2F *h2_Edep_thin_XY_150 = (TH2F*)file150->Get("h2_Edep_thin_XY");
  TH2F *h2_Edep_thin_XY_200 = (TH2F*)file200->Get("h2_Edep_thin_XY");

  h2_Edep_thin_XY_075->SetTitle("THIN Layer Hit 2Dim weighted with Edep (0.075mm)");
  h2_Edep_thin_XY_100->SetTitle("THIN Layer Hit 2Dim weighted with Edep (0.100mm)");
  h2_Edep_thin_XY_150->SetTitle("THIN Layer Hit 2Dim weighted with Edep (0.150mm)");
  h2_Edep_thin_XY_200->SetTitle("THIN Layer Hit 2Dim weighted with Edep (0.200mm)");

  h2_Edep_thin_XY_075->SetMaximum(50);
  h2_Edep_thin_XY_100->SetMaximum(50);
  h2_Edep_thin_XY_150->SetMaximum(50);
  h2_Edep_thin_XY_200->SetMaximum(50);


  TH2F *h2_Edep_thick_XY_075 = (TH2F*)file075->Get("h2_Edep_thick_XY");
  TH2F *h2_Edep_thick_XY_100 = (TH2F*)file100->Get("h2_Edep_thick_XY");
  TH2F *h2_Edep_thick_XY_150 = (TH2F*)file150->Get("h2_Edep_thick_XY");
  TH2F *h2_Edep_thick_XY_200 = (TH2F*)file200->Get("h2_Edep_thick_XY");

  h2_Edep_thick_XY_075->SetTitle("THICK Layer Hit 2Dim weighted with Edep (0.075mm)");
  h2_Edep_thick_XY_100->SetTitle("THICK Layer Hit 2Dim weighted with Edep (0.100mm)");
  h2_Edep_thick_XY_150->SetTitle("THICK Layer Hit 2Dim weighted with Edep (0.150mm)");
  h2_Edep_thick_XY_200->SetTitle("THICK Layer Hit 2Dim weighted with Edep (0.200mm)");

  h2_Edep_thick_XY_075->SetMaximum(100);
  h2_Edep_thick_XY_100->SetMaximum(100);
  h2_Edep_thick_XY_150->SetMaximum(100);
  h2_Edep_thick_XY_200->SetMaximum(100);


  TCanvas *c1 = new TCanvas("c1","c1",800,600);
  c1->SetLogy();
  TLegend *leg = new TLegend(0.6,0.5,0.98,0.8);
  leg->AddEntry(h1_Edep_thick_075,"0.075mm paint","l");
  leg->AddEntry(h1_Edep_thick_100,"0.100mm paint","l");
  leg->AddEntry(h1_Edep_thick_150,"0.150mm paint","l");
  leg->AddEntry(h1_Edep_thick_200,"0.200mm paint","l");

  gStyle->SetOptStat(0);
  h1_Edep_thick_075->Draw();

  h1_Edep_thick_100->Draw("SAME");
  h1_Edep_thick_150->Draw("SAME");
  h1_Edep_thick_200->Draw("SAME");
  leg->Draw();

  c1->Print("pictures/Edep_thick_tiles.pdf");

  h1_Edep_thin_075->Draw();
  h1_Edep_thin_100->Draw("SAME");
  h1_Edep_thin_150->Draw("SAME");
  h1_Edep_thin_200->Draw("SAME");
  leg->Draw();

  c1->Print("pictures/Edep_thin_tiles.pdf");

  gStyle->SetOptStat(1);

  TCanvas *c2 = new TCanvas("c2","c2",800,800);
  c2->Divide(2,2);
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

  c2->Print("pictures/Theta_Z.pdf");

  c2->cd(1);
  h2_Edep_thin_z_075->Draw("COLZ");
  c2->cd(2);
  h2_Edep_thin_z_100->Draw("COLZ");
  c2->cd(3);
  h2_Edep_thin_z_150->Draw("COLZ");
  c2->cd(4);
  h2_Edep_thin_z_200->Draw("COLZ");

  c2->Print("pictures/Edep_Thin_Z.pdf");

  c2->cd(1);
  h2_Edep_thick_z_075->Draw("COLZ");
  c2->cd(2);
  h2_Edep_thick_z_100->Draw("COLZ");
  c2->cd(3);
  h2_Edep_thick_z_150->Draw("COLZ");
  c2->cd(4);
  h2_Edep_thick_z_200->Draw("COLZ");

  c2->Print("pictures/Edep_Thick_Z.pdf");

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

  c2->Print("pictures/Edep_thick_XY.pdf");


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

  c2->Print("pictures/Edep_thin_XY.pdf");

}
