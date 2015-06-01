{
  TProof::Open("");
  TChain *mc_edgen = new TChain("Physics");
  mc_edgen->Add("out100.txt.root");
  mc_edgen->SetProof();
  mc_edgen->Process("analysis075.C++");

}
