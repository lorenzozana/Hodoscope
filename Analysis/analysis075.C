#define analysis075_cxx
// The class definition in analysis075.h has been generated automatically
// by the ROOT utility TTree::MakeSelector(). This class is derived
// from the ROOT class TSelector. For more information on the TSelector
// framework see $ROOTSYS/README/README.SELECTOR or the ROOT User Manual.

// The following methods are defined in this file:
//    Begin():        called every time a loop on the tree starts,
//                    a convenient place to create your histograms.
//    SlaveBegin():   called after Begin(), when on PROOF called only on the
//                    slave servers.
//    Process():      called for each event, in this function you decide what
//                    to read and fill your histograms.
//    SlaveTerminate: called at the end of the loop on the tree, when on PROOF
//                    called only on the slave servers.
//    Terminate():    called at the end of the loop on the tree,
//                    a convenient place to draw/fit your histograms.
//
// To use this file, try the following session on your Tree T:
//
// Root > T->Process("analysis075.C")
// Root > T->Process("analysis075.C","some options")
// Root > T->Process("analysis075.C+")
//

#include "analysis075.h"
#include <TH2.h>
#include <TStyle.h>


void analysis075::Begin(TTree * /*tree*/)
{
   // The Begin() function is called at the start of the query.
   // When running with PROOF Begin() is only called on the client.
   // The tree argument is deprecated (on PROOF 0 is passed).

   TString option = GetOption();

}

void analysis075::SlaveBegin(TTree * /*tree*/)
{
   // The SlaveBegin() function is called after the Begin() function.
   // When running with PROOF SlaveBegin() is called on each slave server.
   // The tree argument is deprecated (on PROOF 0 is passed).

   TString option = GetOption();

   h1_theta = new TH1F("h1_theta","#theta e^{-} at target",100,0.,10.);
   h1_Edep_thin  = new TH1F("h1_Edep_thin","Energy deposited Thin Tyles (MeV) for R_{hit} < 173mm",100,0.,10.);
   h1_Edep_thick  = new TH1F("h1_Edep_thick","Energy deposited Thick Tyles (MeV) for R_{hit} < 173mm",100,0.,10.);
   h1_z = new TH1F("h1_z","Z e^{-} at hit",300,1810.,1840.);
   h2_Edep_thin_z  = new TH2F("h2_Edep_thin_z","Energy deposited Thin Tyles (MeV) vs Z for R_{hit} < 173mm; Z(mm); E_{dep}(MeV)",100,1810.,1840.,100,0.,10.);
   h2_Edep_thick_z  = new TH2F("h2_Edep_thick_z","Energy deposited Thick Tyles (MeV) vs Z for R_{hit} < 173mm; Z(mm); E_{dep}(MeV)",100,1810,1840,100,0.,10.);
   h2_XY = new TH2F("h2_XY","Hit 2Dim; X(mm); Y(mm)",100,-15.0,173.0,100,0.0,173.0);
   h2_Edep_XY = new TH2F("h2_Edep_XY","Hit 2Dim weighted with Edep; X(mm); Y(mm)",100,-15.0,173.0,100,0.0,173.0);
   h2_theta_z = new TH2F("h2_theta_z","#theta e^{-} at target vs z",100,1810,1840,100,0.,6.);


   fOutput->Add(h1_theta);
   fOutput->Add(h1_Edep_thin);
   fOutput->Add(h1_Edep_thick);
   fOutput->Add(h1_z);
   fOutput->Add(h2_Edep_thin_z);
   fOutput->Add(h2_Edep_thick_z);
   fOutput->Add(h2_XY);
   fOutput->Add(h2_Edep_XY);
   fOutput->Add(h2_theta_z);
					  
}

Bool_t analysis075::Process(Long64_t entry)
{
   // The Process() function is called for each entry in the tree (or possibly
   // keyed object in the case of PROOF) to be processed. The entry argument
   // specifies which entry in the currently loaded tree is to be processed.
   // It can be passed to either analysis075::GetEntry() or TBranch::GetEntry()
   // to read either all or the required parts of the data. When processing
   // keyed objects with PROOF, the object is already loaded and is available
   // via the fObject pointer.
   //
   // This function should contain the "body" of the analysis. It can contain
   // simple or elaborate selection criteria, run algorithms on the data
   // of the event and typically fill histograms.
   //
   // The processing can be stopped by calling Abort().
   //
   // Use fStatus to set the return value of TTree::Process().
   //
   // The return value is currently not used.

  b_n_hits->GetEntry(entry);
  
  b_P_X_v->GetEntry(entry);
  b_P_Y_v->GetEntry(entry);
  b_P_Z_v->GetEntry(entry);
  b_Edep->GetEntry(entry);
  b_X->GetEntry(entry);
  b_Y->GetEntry(entry);
  b_Z->GetEntry(entry);
  b_Layer->GetEntry(entry);
  b_Sector->GetEntry(entry);
  b_Id->GetEntry(entry);

  int sector_at = -1;
  int id_at = -1;
  int layer_at = -1;
  double threshold = 0.1;  // Threshold Edep
  double theta = 0.;
  double Edep_val[2][4][29]; // Construct array of energy deposited in Hodoscope
  double Z_val[2][4][29];
  double X_val[2][4][29];
  double Y_val[2][4][29];

  for (int i=0; i<2; i++) {
    for (int j=0; j<4; j++) {
      for (int k=0; k<29; k++) {
	Edep_val[i][j][k] = 0.0;
        X_val[i][j][k] = 0.0;
	Y_val[i][j][k] = 0.0;
	Z_val[i][j][k] = 0.0;
	
      }
    }
  }
 
  if (P_Z>0.0) theta = TMath::ATan(pow(pow(P_X,2)+pow(P_Y,2),0.5)/P_Z) / TMath::Pi() * 180;
  double rad_hit = 0.;
  for (UInt_t ii=0; ii<X->size(); ii++) {
    rad_hit = pow(pow(X->at(ii),2)+pow(Y->at(ii),2),0.5);
    if (rad_hit < 172.0) { // 175mm max - 3mm thickness of out layer
      if ( Edep->at(ii) > threshold && Layer->at(ii)>-1 && Sector->at(ii)>-1 && Id->at(ii)>-1 ) {
	// valid hit
	Edep_val[Layer->at(ii)][Sector->at(ii)][Id->at(ii)] = Edep->at(ii);
	X_val[Layer->at(ii)][Sector->at(ii)][Id->at(ii)] = X->at(ii);
	Y_val[Layer->at(ii)][Sector->at(ii)][Id->at(ii)] = Y->at(ii);
	Z_val[Layer->at(ii)][Sector->at(ii)][Id->at(ii)] = Z->at(ii);

      }
    }
  }

  for (int j=0; j<4; j++) {
    for (int k=0; k<29; k++) {
      if (Edep_val[0][j][k] > threshold && Edep_val[1][j][k] >threshold ) {
	h1_Edep_thin->Fill(Edep_val[0][j][k]);
	h1_Edep_thick->Fill(Edep_val[1][j][k]);
	h1_theta->Fill(theta);
	h1_z->Fill(Z_val[0][j][k]);
	h1_z->Fill(Z_val[1][j][k]);
	h2_Edep_thin_z->Fill(Z_val[0][j][k],Edep_val[0][j][k]);
	h2_Edep_thick_z->Fill(Z_val[1][j][k],Edep_val[1][j][k]);
	h2_XY->Fill(X_val[1][j][k],Y_val[1][j][k]);
	h2_Edep_XY->Fill(X_val[1][j][k],Y_val[1][j][k],Edep_val[1][j][k]);
	h2_theta_z->Fill(Z_val[0][j][k],theta);
	h2_theta_z->Fill(Z_val[1][j][k],theta);

      }
    }
  }

   return kTRUE;
}

void analysis075::SlaveTerminate()
{
   // The SlaveTerminate() function is called after all entries or objects
   // have been processed. When running with PROOF SlaveTerminate() is called
   // on each slave server.

}

void analysis075::Terminate()
{

  TFile file_out("analysis_output.root","recreate");
  TList *outlist = GetOutputList();
  
  outlist->Write();
  file_out.Write();
  file_out.Close();

   // The Terminate() function is the last function to be called during
   // a query. It always runs on the client, it can be used to present
   // the results graphically or save the results to file.

}
