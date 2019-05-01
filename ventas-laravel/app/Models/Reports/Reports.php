<?php

namespace App\Models\Reports;

use Illuminate\Database\Eloquent\Model;
use DB;
class Reports extends Model
{

  /**
   * ejecuta procedimiento almacenado de estadisdtica
   */
   public static function getMostSelledProducts($empresa,$inicio,$fin)
   {
   $result=  DB::select('call sp_productos_mas_vendidos("'.$empresa.'", "'.$inicio.'","'.$fin.'")');
   return $result;
   }

   /**
    * procedimiento almacenado de ventas diarias
    */
   public static function getDailySalledSummary($empresa,$inicio)
   {
   $result=  DB::select('call sp_ventas_diario("'.$empresa.'", "'.$inicio.'")');
   return $result;
   }

}
