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

class DailySummary extends Controller
{

    use DateTime;

    /**
     * Display a listing of the resource.
     *
     * @return Response
     */
    public function index()
    {
      try {


        $desde =  date('Y-m-d');
        if(request('fecha')){
          $desde = request('fecha');
        }


        $empresa= session('company_id');
        $data = Reports::getDailySalledSummary($empresa,$desde);
        $view_template = 'reports.daily_summary.index';
        return view($view_template,compact('data'));

      } catch (\Exception $e) {
        return $e;
      }

    }


}
