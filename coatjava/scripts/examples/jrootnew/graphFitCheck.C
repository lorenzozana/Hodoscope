{

   TCanvas *c1 = new TCanvas("c1","A Simple Graph with error bars",200,10,900,700);

   //c1->SetFillColor(42);
   c1->SetGrid();
	//c1->GetFrame()->SetFillColor(21);
   //c1->GetFrame()->SetBorderSize(12);

   const Int_t n = 6;

   Float_t x[n]   = {1.0, 2.75,  3.0,  3.5, 5.0, 6.0};
   Float_t y[n]   = {1.2, 1.2 , 0.78, 0.96, 0.4, 0.2};
   Float_t ex[n]  = {0.0,0.0,0.0,0.0,0.0,0.0};
   Float_t ey[n]  = {0.05, 0.2, 0.2, 0.2, 0.2, 0.05};
   //Float_t eyZ[n] = {0.05, 0.0, 0.0, 0.0, 0.2, 0.05};
   Float_t eyZ[n] = {0.05, 0.2, 0.2, 0.2, 0.0, 0.0};
   Float_t eyAZ[n] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.00};
   
   TGraph       *gr   = new TGraph(n,x,y);
   TGraphErrors *grE  = new TGraphErrors(n,x,y,ex,ey);
   TGraphErrors *grZ  = new TGraphErrors(n,x,y,ex,eyZ);
   TGraphErrors *grAZ = new TGraphErrors(n,x,y,ex,eyAZ);

   const char line[128];
   
   TText *text = new TText();
   text->SetTextSize(0.06);

   c1->Divide(2,2);

   c1->cd(1);
   gr->SetTitle("TGraph fit with POL1");
   gr->SetMarkerColor(4);
   gr->SetMarkerStyle(21);
   gr->Draw("AP");

   TF1  *func = new TF1("func","pol1",0.5,6.5);
   gr->Fit(func,"RME");
   
   sprintf(line,"p0  %.6f +/- %.7f",func->GetParameter(0),func->GetParError(0));
   text->DrawText(1.0,0.4,line);
   sprintf(line,"p1  %.6f +/- %.7f",func->GetParameter(1),func->GetParError(1));
   text->DrawText(1.0,0.2,line);



   c1->cd(2);
   grE->SetTitle("TGraphErrors with set ERRORS");
   grE->SetMarkerColor(4);
   grE->SetMarkerStyle(21);
   grE->Draw("AEP");

   TF1  *func2 = new TF1("func2","pol1",0.5,6.5);
   grE->Fit(func2);

   sprintf(line,"p0  %.6f +/- %.7f",func2->GetParameter(0),func2->GetParError(0));
   text->DrawText(1.0,0.4,line);
   sprintf(line,"p1  %.6f +/- %.7f",func2->GetParameter(1),func2->GetParError(1));
   text->DrawText(1.0,0.2,line);


   c1->cd(3);
   grZ->SetTitle("TGraphErrors with points 2-4 errors set to 0");
   grZ->SetMarkerColor(4);
   grZ->SetMarkerStyle(21);
   grZ->Draw("AEP");

   TF1  *func3 = new TF1("func3","pol1",0.5,6.5);
   grZ->Fit(func3);

   sprintf(line,"p0  %.6f +/- %.7f",func3->GetParameter(0),func3->GetParError(0));
   text->DrawText(1.0,0.4,line);
   sprintf(line,"p1  %.6f +/- %.7f",func3->GetParameter(1),func3->GetParError(1));
   text->DrawText(1.0,0.2,line);

   c1->cd(4);
   grAZ->SetTitle("TGraphErrors with all errors set to 0");
   grAZ->SetMarkerColor(4);
   grAZ->SetMarkerStyle(21);
   grAZ->Draw("AEP");

   TF1  *func4 = new TF1("func4","pol1",0.5,6.5);
   grAZ->Fit(func4);

   sprintf(line,"p0  %.6f +/- %.7f",func4->GetParameter(0),func4->GetParError(0));
   text->DrawText(1.0,0.4,line);
   sprintf(line,"p1  %.6f +/- %.7f",func4->GetParameter(1),func4->GetParError(1));
   text->DrawText(1.0,0.2,line);

   c1->Update();


   cout << "FUNCTION 1  NDF = " << func->GetNDF()  << "  " <<  func->GetChisquare() << endl;
   cout << "FUNCTION 2  NDF = " << func2->GetNDF() << "  " <<  func2->GetChisquare() << endl;
   cout << "FUNCTION 3  NDF = " << func3->GetNDF() << "  " <<  func3->GetChisquare() << endl;
   cout << "FUNCTION 4  NDF = " << func4->GetNDF() << "  " <<  func4->GetChisquare() << endl;
}