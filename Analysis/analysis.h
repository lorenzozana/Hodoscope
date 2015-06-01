//////////////////////////////////////////////////////////
// This class has been automatically generated on
// Wed Dec  3 13:36:57 2014 by ROOT version 5.34/09
// from TChain T/
//////////////////////////////////////////////////////////

#ifndef analysis_h
#define analysis_h

#include <TROOT.h>
#include <TChain.h>
#include <TFile.h>
#include <TSelector.h>
#include <TLorentzVector.h>
#include <TVector3.h>
#include <TMath.h>


class   TH1F;
class   TH2F;
// Header file for the classes stored in the TTree if any.

// Fixed size dimensions of array or collections stored in the TTree if any.

class analysis : public TSelector {
public :

  TH1F           *h1_phi;
  TH1F           *h1_costheta;
  TH1F           *h1_mass;
  TH1F           *h1_costheta2;
  TH1F           *h1_theta_pim;

   TTree          *fChain;   //!pointer to the analyzed TTree or TChain

   // Declaration of leaf types
   Int_t           n_part;
   Double_t        theta[5];   //[n_part]
   Double_t        phi[5];   //[n_part]
   Double_t        x;
   Double_t        Ein_beam;
   Double_t        Ef[5];   //[n_part]
   Double_t        Q2;
   Double_t        nu;
   Double_t        W;
   Double_t        y;
   Int_t           Z_ion;
   Int_t           N_ion;
   Int_t           particle_id[5];   //[n_part]
   Int_t           charge[5];   //[n_part]
   Double_t        pf[5];   //[n_part]
   Double_t        px[5];   //[n_part]
   Double_t        py[5];   //[n_part]
   Double_t        pz[5];   //[n_part]
   Double_t        vx[5];   //[n_part]
   Double_t        vy[5];   //[n_part]
   Double_t        vz[5];   //[n_part]
   Double_t        weight;

   // List of branches
   TBranch        *b_n_part;   //!
   TBranch        *b_theta;   //!
   TBranch        *b_phi;   //!
   TBranch        *b_x;   //!
   TBranch        *b_Ein_beam;   //!
   TBranch        *b_Ef;   //!
   TBranch        *b_Q2;   //!
   TBranch        *b_nu;   //!
   TBranch        *b_W;   //!
   TBranch        *b_y;   //!
   TBranch        *b_Z_ion;   //!
   TBranch        *b_N_ion;   //!
   TBranch        *b_particle_id;   //!
   TBranch        *b_charge;   //!
   TBranch        *b_pf;   //!
   TBranch        *b_px;   //!
   TBranch        *b_py;   //!
   TBranch        *b_pz;   //!
   TBranch        *b_vx;   //!
   TBranch        *b_vy;   //!
   TBranch        *b_vz;   //!
   TBranch        *b_weight;   //!

   analysis(TTree * /*tree*/ =0) : fChain(0) { }
   virtual ~analysis() { }
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

   ClassDef(analysis,0);
};

#endif

#ifdef analysis_cxx
void analysis::Init(TTree *tree)
{
   // The Init() function is called when the selector needs to initialize
   // a new tree or chain. Typically here the branch addresses and branch
   // pointers of the tree will be set.
   // It is normally not necessary to make changes to the generated
   // code, but the routine can be extended by the user if needed.
   // Init() will be called many times when running on PROOF
   // (once per file to be processed).

   // Set branch addresses and branch pointers
   if (!tree) return;
   fChain = tree;
   fChain->SetMakeClass(1);

   fChain->SetBranchAddress("n_part", &n_part, &b_n_part);
   fChain->SetBranchAddress("theta", theta, &b_theta);
   fChain->SetBranchAddress("phi", phi, &b_phi);
   fChain->SetBranchAddress("x", &x, &b_x);
   fChain->SetBranchAddress("Ein_beam", &Ein_beam, &b_Ein_beam);
   fChain->SetBranchAddress("Ef", Ef, &b_Ef);
   fChain->SetBranchAddress("Q2", &Q2, &b_Q2);
   fChain->SetBranchAddress("nu", &nu, &b_nu);
   fChain->SetBranchAddress("W", &W, &b_W);
   fChain->SetBranchAddress("y", &y, &b_y);
   fChain->SetBranchAddress("Z_ion", &Z_ion, &b_Z_ion);
   fChain->SetBranchAddress("N_ion", &N_ion, &b_N_ion);
   fChain->SetBranchAddress("particle_id", particle_id, &b_particle_id);
   fChain->SetBranchAddress("charge", charge, &b_charge);
   fChain->SetBranchAddress("pf", pf, &b_pf);
   fChain->SetBranchAddress("px", px, &b_px);
   fChain->SetBranchAddress("py", py, &b_py);
   fChain->SetBranchAddress("pz", pz, &b_pz);
   fChain->SetBranchAddress("vx", vx, &b_vx);
   fChain->SetBranchAddress("vy", vy, &b_vy);
   fChain->SetBranchAddress("vz", vz, &b_vz);
   fChain->SetBranchAddress("weight", &weight, &b_weight);
}

Bool_t analysis::Notify()
{
   // The Notify() function is called when a new file is opened. This
   // can be either for a new TTree in a TChain or when when a new TTree
   // is started when using PROOF. It is normally not necessary to make changes
   // to the generated code, but the routine can be extended by the
   // user if needed. The return value is currently not used.

   return kTRUE;
}

#endif // #ifdef analysis_cxx
