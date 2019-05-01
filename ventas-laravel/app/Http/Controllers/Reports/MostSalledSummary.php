<?php

namespace App\Http\Controllers\Reports;

use App\Http\Controllers\Controller;
use App\Models\Income\Invoice;
use App\Models\Income\InvoicePayment;
use App\Models\Income\Revenue;
use App\Models\Expense\Bill;
use App\Models\Expense\BillPayment;
use App\Models\Expense\Payment;
use App\Models\Setting\Category;
use App\Models\Reports\Reports;
use App\Traits\DateTime;
use App\Models\Auth\User;
use App\Models\Auth\Role;
use DB;
use Charts;
use Date;

class MostSalledSummary extends Controller
{


    /**
     * Display a listing of the resource.
     *
     * @return Response
     */
    public function getMostSelledProducts()
    {

      try {
      $desde =  date('Y-m-d');
      $hasta =  date('Y-m-d');
      if(request('desde')){
        $desde = request('desde');
      }
      if(request('hasta')){
          $hasta = request('hasta');
      }


      $empresa= session('company_id');
      $data = Reports::getMostSelledProducts($empresa,$desde,$hasta);
      $view_template = 'reports.most_salled.index';
      return view($view_template,compact('data'));

    } catch (\Exception $e) {
      return $e;
    }

    }


}
