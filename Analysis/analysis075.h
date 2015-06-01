//////////////////////////////////////////////////////////
// This class has been automatically generated on
// Fri May 29 14:32:15 2015 by ROOT version 5.34/21
// from TChain Physics/
//////////////////////////////////////////////////////////

#ifndef analysis075_h
#define analysis075_h

#include <TROOT.h>
#include <TChain.h>
#include <TFile.h>
#include <TSelector.h>
#include <TLorentzVector.h>
#include <TVector3.h>
#include <TMath.h>

// Header file for the classes stored in the TTree if any.
#include <vector>
#include <vector>


// Fixed size dimensions of array or collections stored in the TTree if any.
class   TH1F;
class   TH2F;

class analysis075 : public TSelector {
public :
  
  TH1F            *h1_Edep_thin;
  TH1F            *h1_Edep_thick;
  TH1F            *h1_theta;
  TH1F            *h1_z;
  TH2F            *h2_Edep_thin_z;
  TH2F            *h2_Edep_thick_z;
  TH2F            *h2_XY;
  TH2F            *h2_Edep_XY;
  TH2F            *h2_theta_z;

   TTree          *fChain;   //!pointer to the analyzed TTree or TChain

   // Declaration of leaf types
   Int_t           N_hits;
   Int_t           PID;
   Double_t        P_X;
   Double_t        P_Y;
   Double_t        P_Z;
   std::vector<double>  *Edep;
   std::vector<double>  *X;
   std::vector<double>  *Y;
   std::vector<double>  *Z;
   std::vector<int>     *Layer;
   std::vector<int>     *Sector;
   std::vector<int>     *Id;

   // List of branches
   TBranch        *b_n_hits;   //!
   TBranch        *b_PID_v;   //!
   TBranch        *b_P_X_v;   //!
   TBranch        *b_P_Y_v;   //!
   TBranch        *b_P_Z_v;   //!
   TBranch        *b_Edep;   //!
   TBranch        *b_X;   //!
   TBranch        *b_Y;   //!
   TBranch        *b_Z;   //!
   TBranch        *b_Layer;   //!
   TBranch        *b_Sector;   //!
   TBranch        *b_Id;   //!

   analysis075(TTree * /*tree*/ =0) : fChain(0) { }
   virtual ~analysis075() { }
   virtual Int_t   Version() const { return 2; }
   virtual void    Begin(TTree *tree);
   virtual void    SlaveBegin(TTree *tree);
   virtual void    Init(TTree *tree);
   virtual Bool_t  Notify();
   virtual Bool_t  Process(Long64_t entry);
   virtual Int_t   GetEntry(Long64_t entry, Int_t getall = 0) { return fChain ? fChain->GetTree()->GetEntry(entry, getall) : 0; }
   virtual void    SetOption(const char *option) { fOption = option; }
   virtual void    SetObject(TObject *obj) { fObject = obj; }
   virtual void    SetInputList(TList *input) { fInput = input; }
   virtual TList  *GetOutputList() const { return fOutput; }
   virtual void    SlaveTerminate();
   virtual void    Terminate();

   ClassDef(analysis075,0);
};

#endif

#ifdef analysis075_cxx
void analysis075::Init(TTree *tree)
{
   // The Init() function is called when the selector needs to initialize
   // a new tree or chain. Typically here the branch addresses and branch
   // pointers of the tree will be set.
   // It is normally not necessary to make changes to the generated
   // code, but the routine can be extended by the user if needed.
   // Init() will be called many times when running on PROOF
   // (once per file to be processed).

   // Set object pointer
   Edep = 0;
   X = 0;
   Y = 0;
   Z = 0;
   Layer = 0;
   Sector = 0;
   Id = 0;
   // Set branch addresses and branch pointers
   if (!tree) return;
   fChain = tree;
   fChain->SetMakeClass(1);

   fChain->SetBranchAddress("N_hits", &N_hits, &b_n_hits);
   fChain->SetBranchAddress("PID", &PID, &b_PID_v);
   fChain->SetBranchAddress("P_X", &P_X, &b_P_X_v);
   fChain->SetBranchAddress("P_Y", &P_Y, &b_P_Y_v);
   fChain->SetBranchAddress("P_Z", &P_Z, &b_P_Z_v);
   fChain->SetBranchAddress("Edep", &Edep, &b_Edep);
   fChain->SetBranchAddress("X", &X, &b_X);
   fChain->SetBranchAddress("Y", &Y, &b_Y);
   fChain->SetBranchAddress("Z", &Z, &b_Z);
   fChain->SetBranchAddress("Layer", &Layer, &b_Layer);
   fChain->SetBranchAddress("Sector", &Sector, &b_Sector);
   fChain->SetBranchAddress("Id", &Id, &b_Id);
}

Bool_t analysis075::Notify()
{
   // The Notify() function is called when a new file is opened. This
   // can be either for a new TTree in a TChain or when when a new TTree
   // is started when using PROOF. It is normally not necessary to make changes
   // to the generated code, but the routine can be extended by the
   // user if needed. The return value is currently not used.

   return kTRUE;
}

#endif // #ifdef analysis075_cxx
