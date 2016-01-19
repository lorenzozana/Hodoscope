{
//=========Macro generated from canvas: c1/c1
//=========  (Mon Jan 26 12:11:14 2015) by ROOT version5.34/22
   TCanvas *c1 = new TCanvas("clara_scaling", "clara_scaling",2846,500,953,814);
   c1->Range(-6.458617,-0.03894214,39.60651,0.3484297);
   c1->SetFillColor(0);
   c1->SetBorderMode(0);
   c1->SetBorderSize(2);
   c1->SetGridx();
   c1->SetGridy();
   c1->SetLeftMargin(0.1402062);
   c1->SetFrameBorderMode(0);
   c1->SetFrameBorderMode(0);
   
   TH2F *htemp__3 = new TH2F("htemp__3","x:(Entry$+1)",40,0,35,40,0,0.31);
   htemp__3->SetDirectory(0);
   htemp__3->SetStats(0);

   Int_t ci;      // for color index setting
   TColor *color; // for color definition with alpha
   ci = TColor::GetColor("#000099");
   htemp__3->SetLineColor(ci);
   htemp__3->GetXaxis()->SetTitle("Threads");
   htemp__3->GetXaxis()->SetRange(1,40);
   htemp__3->GetXaxis()->SetLabelFont(42);
   htemp__3->GetXaxis()->SetLabelSize(0.05);
   htemp__3->GetXaxis()->SetTitleSize(0.05);
   htemp__3->GetYaxis()->SetTitle("Reconstruction Rate (kHz)");
   htemp__3->GetYaxis()->SetRange(1,40);
   htemp__3->GetYaxis()->SetLabelFont(42);
   htemp__3->GetYaxis()->SetLabelSize(0.05);
   htemp__3->GetYaxis()->SetTitleSize(0.05);
   htemp__3->GetYaxis()->SetTitleOffset(1.29);
   htemp__3->GetZaxis()->SetLabelFont(42);
   htemp__3->GetZaxis()->SetLabelSize(0.035);
   htemp__3->GetZaxis()->SetTitleSize(0.035);
   htemp__3->GetZaxis()->SetTitleFont(42);
   htemp__3->Draw("");
   
   TPaveText *pt = new TPaveText(0.3632194,0.9349347,0.6367806,0.995,"blNDC");
   pt->SetName("title");
   pt->SetBorderSize(0);
   pt->SetFillColor(0);
   pt->SetFillStyle(0);
   pt->SetTextFont(42);
   pt->Draw();
   
   TGraph *graph = new TGraph(32);
   graph->SetName("Graph0");
   graph->SetTitle("Graph");

   ci = TColor::GetColor("#000099");
   graph->SetLineColor(ci);
   graph->SetLineWidth(3);
   graph->SetMarkerColor(2);
   graph->SetMarkerStyle(3);
   graph->SetMarkerSize(2.4);
   graph->SetPoint(0,1,0.0120000001);
   graph->SetPoint(1,2,0.02400000021);
   graph->SetPoint(2,3,0.03500000015);
   graph->SetPoint(3,4,0.04699999839);
   graph->SetPoint(4,5,0.05799999833);
   graph->SetPoint(5,6,0.06899999827);
   graph->SetPoint(6,7,0.07999999821);
   graph->SetPoint(7,8,0.09099999815);
   graph->SetPoint(8,9,0.1010000035);
   graph->SetPoint(9,10,0.1120000035);
   graph->SetPoint(10,11,0.1230000034);
   graph->SetPoint(11,12,0.1330000013);
   graph->SetPoint(12,13,0.1430000067);
   graph->SetPoint(13,14,0.1529999971);
   graph->SetPoint(14,15,0.1630000025);
   graph->SetPoint(15,16,0.172999993);
   graph->SetPoint(16,17,0.1819999963);
   graph->SetPoint(17,18,0.1899999976);
   graph->SetPoint(18,19,0.199000001);
   graph->SetPoint(19,20,0.2060000002);
   graph->SetPoint(20,21,0.2160000056);
   graph->SetPoint(21,22,0.2230000049);
   graph->SetPoint(22,23,0.2310000062);
   graph->SetPoint(23,24,0.2370000035);
   graph->SetPoint(24,25,0.2450000048);
   graph->SetPoint(25,26,0.25);
   graph->SetPoint(26,27,0.2569999993);
   graph->SetPoint(27,28,0.2630000114);
   graph->SetPoint(28,29,0.2689999938);
   graph->SetPoint(29,30,0.273999989);
   graph->SetPoint(30,31,0.2800000012);
   graph->SetPoint(31,32,0.2829999924);
   
   TH1F *Graph_Graph_Graph_Graph123 = new TH1F("Graph_Graph_Graph_Graph123","Graph",100,0,35.1);
   Graph_Graph_Graph_Graph123->SetMinimum(0);
   Graph_Graph_Graph_Graph123->SetMaximum(0.3101);
   Graph_Graph_Graph_Graph123->SetDirectory(0);
   Graph_Graph_Graph_Graph123->SetStats(0);

   ci = TColor::GetColor("#000099");
   Graph_Graph_Graph_Graph123->SetLineColor(ci);
   Graph_Graph_Graph_Graph123->GetXaxis()->SetLabelFont(42);
   Graph_Graph_Graph_Graph123->GetXaxis()->SetLabelSize(0.035);
   Graph_Graph_Graph_Graph123->GetXaxis()->SetTitleSize(0.035);
   Graph_Graph_Graph_Graph123->GetXaxis()->SetTitleFont(42);
   Graph_Graph_Graph_Graph123->GetYaxis()->SetLabelFont(42);
   Graph_Graph_Graph_Graph123->GetYaxis()->SetLabelSize(0.035);
   Graph_Graph_Graph_Graph123->GetYaxis()->SetTitleSize(0.035);
   Graph_Graph_Graph_Graph123->GetYaxis()->SetTitleFont(42);
   Graph_Graph_Graph_Graph123->GetZaxis()->SetLabelFont(42);
   Graph_Graph_Graph_Graph123->GetZaxis()->SetLabelSize(0.035);
   Graph_Graph_Graph_Graph123->GetZaxis()->SetTitleSize(0.035);
   Graph_Graph_Graph_Graph123->GetZaxis()->SetTitleFont(42);
   graph->SetHistogram(Graph_Graph_Graph_Graph123);
   
   graph->Draw("p");
   TLine *line = new TLine(1,0.012,25,0.3);
   line->SetLineColor(4);
   line->SetLineStyle(2);
   line->SetLineWidth(4);
   line->Draw();
   
   pt = new TPaveText(1.377205,0.2567107,19.51832,0.2946281,"br");

   ci = TColor::GetColor("#ffffff");
   pt->SetFillColor(ci);

   ci = TColor::GetColor("#000000");
   pt->SetLineColor(ci);

   ci = TColor::GetColor("#7c99d1");
   pt->SetTextColor(ci);
   line = pt->AddLine(0,-3.307692,0,-3.307692);
   TText *text = pt->AddText("AMD (16 cores) Hyper-threaded");
   pt->Draw();
      tex = new TLatex(2.327001,0.3233223,"CLARA Reconstruction Benchmark");
   tex->SetTextColor(38);
   tex->SetLineWidth(2);
   tex->Draw();
   
   pt = new TPaveText(19.80191,0.01530016,33.92725,0.1135504,"br");

   ci = TColor::GetColor("#84c1a3");
   pt->SetFillColor(ci);
   pt->SetTextAlign(12);
   text = pt->AddText("DCHB (Hit Based)");
   text = pt->AddText("EC/PCAL (Calirimeter)");
   text = pt->AddText("DCTB (Time Based)");
   text = pt->AddText("FTOF (Time-of-Flight)");
   text = pt->AddText("HTCC (Cherenkov)");
   text = pt->AddText("EB (Event Builder)");
   pt->Draw();
   c1->Modified();
   c1->cd();
   c1->SetSelected(c1);
   c1->ToggleToolBar();
}
